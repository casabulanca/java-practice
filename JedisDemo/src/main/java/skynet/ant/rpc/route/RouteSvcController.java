package skynet.ant.rpc.route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

import Ice.Current;

import skynet.ant.core.AppContext;
import skynet.ant.core.logger.AntLogger;
import skynet.ant.rpc.RpcHostSvcStatus;
import skynet.ant.rpc.RpcProxy;
import skynet.ant.rpc.RpcProxyManager;
import skynet.ant.rpc.RpcSvcController;
import skynet.ant.rpc.core.RpcParam;
import skynet.ant.rpc.core.RpcRequestPrx;
import skynet.ant.rpc.core.RpcStatus;
import skynet.ant.rpc.data.RemoteSvcIdentity;
import skynet.ant.rpc.ice.IceProxyIdentity;
import skynet.ant.rpc.ice.IceProxyNode;
import skynet.ant.rpc.session.SessionReqProxy;

/**
 * 路由服务
 * <p/>
 * 功能：根据路由参数，获取最优的在线服务路径信息
 *
 * @author lyhu
 */
public final class RouteSvcController extends RpcSvcController<RouteSvcContext> {

    private static final long serialVersionUID = 1833864029109930892L;
    private static final AntLogger logger = AntLogger.getLogger(RouteSvcController.class, AntLogger.platform);

    private final static String logTags = "route-svc-ctrl";
    public final static String ServiceName = "skynet.rpc.route.service";
    private static final int GET_STATUS_TIMEOUT = 500; //获取状态超时时间

    @Autowired
    private RpcProxyManager rpcProxyManager;

    private RouteSvcContext routeSvcContext;

    @Override
    public String getSvcName() {
        return ServiceName;
    }

    public void init(AppContext appContext, RouteSvcContext routeSvcContext) {
        this.routeSvcContext = routeSvcContext;
    }

    /**
     * 根据节点获取 RouteResult
     */
    public byte[] call(byte[] param, Current __current) {

        logger.debug(String.format("route call param:%s", new String(param)), logTags);
        RouteParam routeParam = JSON.parseObject(new String(param), RouteParam.class);
        if (routeParam == null)
            throw new IllegalArgumentException("routeParam is null");

        if (Strings.isNullOrEmpty(routeParam.getSvcName())) {
            //TODO throw exceptions
        }

        logger.debug(routeParam.getTrackId(), String.format("getOptimalRoute param:%s", routeParam), logTags);

        RouteResult routeResult = new RouteResult(routeParam.getTrackId());
        try {
            List<RemoteSvcIdentity> onlineServiceList = getAllRemoteServcieIdentityList(routeParam.getSvcName());

            if (onlineServiceList.size() == 0l) {
                String msg = String.format("not online services.[%s]", routeParam.getSvcName());
                routeResult = new RouteResult(routeParam.getTrackId(), msg);
            } else {
                IceProxyIdentity proxyIdentity = this.routeSvcContext.getRoutePolicy().getRoute(routeParam, onlineServiceList);
                if (proxyIdentity == null) {
                    String msg = String.format("not free session services.[%s]", routeParam.getSvcName());
                    routeResult = new RouteResult(routeParam.getTrackId(), msg);
                } else {
                    routeResult.setProxyIdentity(proxyIdentity);
                }
            }
        } catch (Exception e) {
            logger.error(routeParam.getTrackId(), e.getMessage(), e, logTags);
            routeResult = new RouteResult(routeParam.getTrackId(), e);
        }

        logger.debug(routeResult.trackId, String.format("getOptimalRoute result:%s", routeResult), logTags);

        return routeResult.toString().getBytes();
    }

    Map<String, SessionReqProxy> sessionReqProxyMap = new java.util.concurrent.ConcurrentHashMap<String, SessionReqProxy>();

    private synchronized List<RemoteSvcIdentity> getAllRemoteServcieIdentityList(String svcName) {

        List<RemoteSvcIdentity> statusList = new ArrayList<RemoteSvcIdentity>();

        List<IceProxyNode<RpcRequestPrx, RpcProxy>> nodes = rpcProxyManager.getAllProxyNodes(svcName);

        if (nodes.size() == 0) {
            return statusList;
        }

        ExecutorService exec = Executors.newFixedThreadPool(8);
        CompletionService<RemoteSvcIdentity> completionService = new ExecutorCompletionService<RemoteSvcIdentity>(exec);
        // 并发 执行
        for (final IceProxyNode<RpcRequestPrx, RpcProxy> iceProxyNode : nodes) {
            completionService.submit(new Callable<RemoteSvcIdentity>() {
                public RemoteSvcIdentity call() throws Exception {

                    IceProxyIdentity iceProxyIdentity = iceProxyNode.getSvcState().getIceProxyIdentity();

                    // cache proxy
                    String uri = iceProxyIdentity.getPrxIdentityUri();
                    if (!sessionReqProxyMap.containsKey(uri)) {
                        sessionReqProxyMap.put(uri, new SessionReqProxy(iceProxyIdentity));
                    }
                    // TODO:缓存的 proxy 断了
                    try {
                        RpcHostSvcStatus status = sessionReqProxyMap.get(uri).getStatus(GET_STATUS_TIMEOUT);

                        return new RemoteSvcIdentity(iceProxyIdentity, status);
                    } catch (Exception e) {
                        logger.error(String.format("get SessionServiceStatus error:%s", e.getMessage()), e, logTags);
                        return null;
                    }
                }
            });
        }
        for (int i = 0; i < nodes.size(); i++) {
            try {
                // 超时 1500毫秒 TODO: 后期将 1500s改为配置
                RemoteSvcIdentity status = completionService.take().get(1500, TimeUnit.MILLISECONDS);
                if (status != null) {
                    statusList.add(status);
                }
            } catch (TimeoutException e) {
                logger.error(String.format("get SessionServiceStatus error:%s", e.getMessage()), e, logTags);
            } catch (InterruptedException e) {
                logger.error(String.format("get SessionServiceStatus error:%s", e.getMessage()), e, logTags);
            } catch (ExecutionException e) {
                logger.error(String.format("get SessionServiceStatus error:%s", e.getMessage()), e, logTags);
            }
        }
        exec.shutdown();

        // 按照 freecount 倒排序
        Collections.sort(statusList, new Comparator<RemoteSvcIdentity>() {
            public int compare(RemoteSvcIdentity a1, RemoteSvcIdentity a2) {
                return a1.getRpcHostSvcStatus().getFreeNum() > a2.getRpcHostSvcStatus().getFreeNum() ? -1 : 1;
            }
        });

        return statusList;
    }

    public void close() throws Exception {

        for (Entry<String, SessionReqProxy> item : sessionReqProxyMap.entrySet()) {
            item.getValue().close();
        }
        rpcProxyManager.close();
    }

    // ==不用实现============================================

    /**
     * 不用实现
     */
    public RpcStatus begin(RpcParam param, Current __current) {
        throw new NotImplementedException("begin");
    }

    public RpcStatus post(String sessionId, byte[] content, Current __current) {
        throw new NotImplementedException("post");
    }

    public RpcStatus end(String sessionId, Current __current) {
        throw new NotImplementedException("end");
    }

    @Override
    public boolean isFree() {
        // TODO Auto-generated method stub
        return false;
    }

}
