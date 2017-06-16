package skynet.ant.rpc.route;

import java.util.List;

import com.google.common.base.Strings;

import skynet.ant.rpc.RpcSvcStatus;
import skynet.ant.rpc.data.RemoteSvcIdentity;
import skynet.ant.rpc.ice.IceProxyIdentity;

/**
 * 选取策略：根据tag选择，空闲优先。<br/>
 * 
 * 处理思路：获取所有的在线Session服务，并发请求获取每个服务的内部状态，并按照 空闲排序，获取第一匹配tag的服务。<br/>
 * 如果没有在线服务或都不符合，返回空路由，并说明原因<br/>
 * 
 * @author lyhu
 *
 */
public final class RoutePolicy4Default implements RoutePolicy {

	/**
	 * spring 配置中要保持一致
	 */
	public final static String PolicyName = "skynet.rpc.route.policy.default";

	/**
	 * 
	 * @param routeParam
	 * @param onlineServiceList
	 *            在线服务节点
	 * @return
	 */
	public IceProxyIdentity getRoute(RouteParam routeParam, List<RemoteSvcIdentity> onlineServiceList) {

		RemoteSvcIdentity node = onlineServiceList.get(0);

		if (!Strings.isNullOrEmpty(routeParam.getTag())) {
			// 筛选 符合 tag的并且 有可用会话的 节点
			for (RemoteSvcIdentity remoteSvcIdentity : onlineServiceList) {
				if (routeParam.getTag().equals(remoteSvcIdentity.getRpcHostSvcStatus().getHostStatus().getTag()) && (remoteSvcIdentity.getRpcHostSvcStatus().getFreeNum() > 0)) {
					node = remoteSvcIdentity;
					break;
				}
			}
		}

		// 获取第一个比较闲的
		for (RpcSvcStatus svc : node.getRpcHostSvcStatus().getServiceStatusList()) {
			if (svc.isFree())
				return svc.getIceProxyIdentity();
		}
		return null;

		// 不抛异常了，因为异常会影响性能
		// throw new ServiceException(String.format("not free session
		// services.[%s]", routeParam.getSvcName()));
	}
}
