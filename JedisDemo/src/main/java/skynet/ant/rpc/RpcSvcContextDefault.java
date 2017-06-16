package skynet.ant.rpc;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

import skynet.ant.core.AppContext;
import skynet.ant.core.logger.AntLogger;
import skynet.ant.core.utils.OsUtil;
import skynet.ant.rpc.ice.IceEndpoint;

/**
 * RpcHostContext 的Controller 上下文
 *
 * @author lyhu
 */
public class RpcSvcContextDefault implements RpcSvcContext {

    private static final AntLogger logger = AntLogger.getLogger(RpcHost.class, AntLogger.platform);

    private static final String logTags = "rpc-host-ctx";

    private IceEndpoint endpoint;
    private RpcSvcParam rpcSvcParam;
    private Map<String, Object> paramContext;

    public void init(AppContext appContext, Map<String, Object> paramContext) throws Exception {
        this.paramContext = paramContext;
        String svcContextJson = JSON.toJSONString(paramContext);

        logger.debug(String.format("rpc service context: %s", svcContextJson), logTags);

        RpcSvcParam rpcSvcParam = JSON.parseObject(svcContextJson, RpcSvcParam.class);

        if (Strings.isNullOrEmpty(rpcSvcParam.svc)) {
            //TODO throw exceptions
        }

        // 随机Port 设置Port 优先 获取端口号
        int svcPort = rpcSvcParam.port == 0 ? OsUtil.getRandomPort() : rpcSvcParam.port;

        //// 设置本机Ip
        IceEndpoint endpoint = new IceEndpoint(appContext.getIpAddress(), svcPort);
        endpoint.setProtocol(rpcSvcParam.getRpcProtocol());
        this.endpoint = endpoint;
        this.rpcSvcParam = rpcSvcParam;
    }

    public RpcSvcParam getRpcSvcParam() {
        return rpcSvcParam;
    }

    public IceEndpoint getEndpoint() {
        return endpoint;
    }

    public void close() throws Exception {
    }

    public Map<String, Object> getParamContext() {
        return paramContext;
    }
}