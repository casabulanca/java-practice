package skynet.ant.rpc.route;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;


import skynet.ant.core.AppContext;
import skynet.ant.core.logger.AntLogger;
import skynet.ant.rpc.RpcSvcContext;
import skynet.ant.rpc.data.RouteSvcParam;

/**
 * RPC 路由服务上下文
 * <p/>
 * 包含：路由服务参数，路由策略
 *
 * @author lyhu
 */
public final class RouteSvcContext implements RpcSvcContext {
    private static final AntLogger logger = AntLogger.getLogger(RouteSvcContext.class, AntLogger.platform);

    private final static String logTags = "route-svc-ctx";

    private RouteSvcParam routeSvcParam;
    private RoutePolicy routePolicy;

    public void init(AppContext appContext, Map<String, Object> paramContext) throws Exception {

        String svcContextJson = JSON.toJSONString(paramContext);
        logger.info(String.format("route service context: %s", svcContextJson), logTags);
        this.routeSvcParam = JSON.parseObject(svcContextJson, RouteSvcParam.class);

        String policyName = Strings.isNullOrEmpty(routeSvcParam.policyName) ? RoutePolicy4Default.PolicyName : routeSvcParam.policyName;
        this.routePolicy = appContext.getSpringContext().getBean(policyName, RoutePolicy.class);

        if (this.routePolicy == null) {
            //TODO throw exceptions
        }
    }

    public RouteSvcParam getRouteSvcParam() {
        return routeSvcParam;
    }

    /**
     * 路由策略
     */
    public RoutePolicy getRoutePolicy() {
        return routePolicy;
    }

    public void close() throws Exception {

    }

}