package skynet.ant.rpc.ice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import skynet.ant.core.config.IAntConfigService;
import skynet.ant.core.config.OnlineNodeObserver;
import skynet.ant.core.domain.AntServiceState;
import skynet.ant.core.exception.AntException;
import skynet.ant.core.logger.AntLogger;
import skynet.ant.core.utils.OsUtil;
import skynet.ant.rpc.RpcHostStatus;
 
/**
 * IceProxyManager
 * 
 * 从 Zookeeper 中获取在线的 RpcSvc 配置
 * 
 * @author lyhu
 *
 */
public abstract class IceProxyManager<IPrx extends Ice.ObjectPrx, AppPrx extends IceProxy<IPrx>> implements AutoCloseable {
	private static final AntLogger logger = AntLogger.getLogger(IceProxyManager.class, AntLogger.platform);

	@Autowired
	private IAntConfigService antConfigService;

	/**
	 * 存储所有的服务
	 * 
	 * key:svcUuid value:IceProxyNode
	 */
	Map<String, IceProxyNode<IPrx, AppPrx>> onlineServices = new HashMap<String, IceProxyNode<IPrx, AppPrx>>();

	private void loadOnlineServices(final String svcName) {
		logger.info(String.format("init online %s service begin...", svcName));

		onlineServices = new HashMap<String, IceProxyNode<IPrx, AppPrx>>();
		Map<String, String> onlineSvc = antConfigService.getOnlineSvcNodes(svcName, new OnlineNodeObserver() {
			@Override
			public void update(Map<String, String> onlineSvc) {
				logger.info(" online service changed.");
				initProxy(onlineSvc, svcName);
			}
		});

		initProxy(onlineSvc, svcName);
	}

	private synchronized void initProxy(Map<String, String> onlineSvc, String svcName) {
		logger.info(String.format("online [%s] services count:%d", svcName, onlineSvc.size()));

		// 本次在线服务
		List<String> onlineKeys = new ArrayList<String>();

		// clean invalid services
		this.cleanInvalidService();

		// 添加所有的服务到列表中。
		for (Entry<String, String> item : onlineSvc.entrySet()) {
			AntServiceState svcState = JSON.parseObject(item.getValue(), AntServiceState.class);
			// 统计本次所有的在线服务
			onlineKeys.add(svcState.getUuid());

			// 不包含此Id的服务
			if (!onlineServices.containsKey(svcState.getUuid()) && StringUtils.equals(svcState.getSvcName(), svcName)) {
				RpcHostStatus state = JSON.parseObject(item.getValue(), RpcHostStatus.class);

				AppPrx appPrx = getAppPrx(svcName, state.getEndpoint());
				IceProxyNode<IPrx, AppPrx> node = new IceProxyNode<IPrx, AppPrx>(appPrx, state);
				onlineServices.put(svcState.getUuid(), node);
			}
		}
		// 移除 停止的服务
		Set<String> allonlines = onlineServices.keySet();
		for (String svcUuid : allonlines) {
			if (!onlineKeys.contains(svcUuid)) {
				try {
					onlineServices.get(svcUuid).close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				onlineServices.remove(svcUuid);
			}
		}

		logger.debug(String.format("init online %s service end.", svcName));
	}

	protected abstract AppPrx getAppPrx(String svcName, IceEndpoint endPoint);

	/**
	 * 获取指定名称的服务代理
	 * 
	 * 如果没有服务，将抛 ServiceException
	 * 
	 * @param svcName
	 * @return
	 */
	public synchronized List<AppPrx> getAllProxy(String svcName) {

		List<IceProxyNode<IPrx, AppPrx>> nodes = getAllProxyNodes(svcName);

		// 从onlineServices中获取指定服务名称的 Proxy
		List<AppPrx> proxyList = new ArrayList<AppPrx>();
		for (IceProxyNode<IPrx, AppPrx> node : nodes) {
			proxyList.add(node.getAppPrx());
		}
		return proxyList;
	}

	public synchronized List<IceProxyNode<IPrx, AppPrx>> getAllProxyNodes(String svcName) {

		List<IceProxyNode<IPrx, AppPrx>> proxyList = filterProxyNodes(svcName);
		// 暂时采用 多次加载机制，
		if (proxyList.size() == 0) {
			loadOnlineServices(svcName);
			proxyList = filterProxyNodes(svcName);
		}
		return proxyList;
	}

	private List<IceProxyNode<IPrx, AppPrx>> filterProxyNodes(String svcName) {
		this.cleanInvalidService();

		List<IceProxyNode<IPrx, AppPrx>> proxyList = new ArrayList<IceProxyNode<IPrx, AppPrx>>();
		for (IceProxyNode<IPrx, AppPrx> proxy : onlineServices.values()) {
			if ((proxy != null) && proxy.getAppPrx().getSvcName().equals(svcName)) {
				proxyList.add(proxy);
			}
		}

		logger.debug("the online %s svc count:%d", svcName, proxyList.size());
		return proxyList;
	}

	/**
	 * 获取随机的一个代理服务
	 * 
	 * 优先查找本机的 服务代理
	 * 
	 * @param svcName
	 * @return
	 */
	public synchronized AppPrx getProxy(String svcName) {
		List<AppPrx> proxys = this.getAllProxy(svcName);

		if (proxys.size() == 0)
			throw new AntException("not online [%s] service.", svcName);

		// 优先查找本机的 服务
		List<AppPrx> localPrxs = Lists.newArrayList(Iterables.filter(proxys, new Predicate<AppPrx>() {
			public boolean apply(AppPrx proxy) {
				return OsUtil.isLocalIP(proxy.getIpAddress());
			}
		}));

		AppPrx selectProxy = (localPrxs.size() > 0) ? localPrxs.get(RandomUtils.nextInt(0, localPrxs.size())) : proxys.get(RandomUtils.nextInt(0, proxys.size()));
		return selectProxy;

	}

	/**
	 * 清理无效服务，暂时采用这种比较土的方式 by lyhu
	 */
	private synchronized void cleanInvalidService() {
		// clean invalid services
		Set<String> allOnlineSvcIdList = onlineServices.keySet();
		for (String svcUuid : allOnlineSvcIdList) {
			if (onlineServices.get(svcUuid) == null) {
				onlineServices.remove(svcUuid);
			}
		}
	}

	public final void close() throws Exception {
		logger.debug("IceProxyManager dispose begin..");

		try {
			if (onlineServices != null) {
				for (IceProxyNode<IPrx, AppPrx> proxy : onlineServices.values()) {
					proxy.close();
				}
				onlineServices.clear();
			}
		} catch (Exception e) {
			logger.error(String.format("dispose error: %s", e.getMessage()), e);
			e.printStackTrace();
		}
		if (null != onlineServices){
		    onlineServices.clear();
		}
		logger.debug("IceProxyManager dispose end.");
	}
}
