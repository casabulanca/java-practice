package skynet.ant.rpc.route;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

import skynet.ant.core.logger.AntLogger;
import skynet.ant.core.utils.OsUtil;
import skynet.ant.rpc.RpcProxy;
import skynet.ant.rpc.ice.IceProxyIdentity;

/**
 * 路由服务节点代理<br/>
 * <p/>
 * 此类不需要在ZK体系下调用
 *
 * @author lyhu
 */
public final class RouteReqProxy {

    private static final AntLogger logger = AntLogger.getLogger(RouteReqProxy.class, AntLogger.platform);

    private final static String logTags = "route-prx";

    private RpcProxy rpcProxy;

    public RouteReqProxy(IceProxyIdentity iceProxyIdentity) {
        this.rpcProxy = new RpcProxy(iceProxyIdentity);
    }

    /**
     * F 会话开始
     *
     * @param routeParam 路由请求参数
     * @return 会话Id
     **/
    public RouteResult getRouteResult(RouteParam routeParam) {

        if (routeParam == null)
            throw new IllegalArgumentException("routeParam is null");

        if (Strings.isNullOrEmpty(routeParam.getSvcName())) {
            //TODO throw exceptions
        }
        routeParam.setFrom(OsUtil.getIPAddress());
        byte[] routeResultBytes = rpcProxy.getProxy().call(JSON.toJSONString(routeParam).getBytes());
        RouteResult routeResult = JSON.parseObject(new String(routeResultBytes), RouteResult.class);

        logger.debug(routeResult, logTags);
        return routeResult;
    }
}
