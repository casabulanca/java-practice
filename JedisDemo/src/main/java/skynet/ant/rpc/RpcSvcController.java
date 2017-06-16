package skynet.ant.rpc;


import skynet.ant.core.AppContext;
import skynet.ant.core.logger.AntLogger;
import skynet.ant.rpc.core._RpcRequestDisp;
import skynet.ant.rpc.ice.IceEndpoint;
import skynet.ant.rpc.ice.IceProxyIdentity;

/**
 * RpcSvcController 抽取类
 * <p/>
 * 约定：
 * <p/>
 * <ul>
 * <li>1、在Spring 中的bean配置 前缀必须 与 SvcName与一致</li>
 * <li>2、在Spring中要设置为多例模式</li>
 * </ul>
 * <p/>
 * 例如：
 * <ul>
 * <li>id="skynet.rpc.demo.context" class= "skynet.ant.rpc.demo.DemoSvcContext"
 * </li>
 * <li>id="skynet.rpc.demo.controller"
 * class="skynet.ant.rpc.demo.DemoSvcController" scope="prototype"</li>
 * </ul>
 *
 * @author lyhu
 */
public abstract class RpcSvcController<T extends RpcSvcContext> extends _RpcRequestDisp implements AutoCloseable {

    private static final AntLogger logger = AntLogger.getLogger(RpcSvcController.class, AntLogger.platform);

    private static final long serialVersionUID = -2420001353492266266L;
    private static final String logTags = "rpc-svc";

    /**
     * 服务名称
     *
     * @return
     */
    public abstract String getSvcName();

    public final void init1(AppContext appContext, RpcSvcContext rpcSvcContext) throws Exception {
        logger.debug("init", logTags);
        @SuppressWarnings("unchecked")
        T svcContext = (T) rpcSvcContext;
        init(appContext, svcContext);
    }

    public abstract void init(AppContext appContext, T svcContext) throws Exception;

    /**
     * 是否空闲
     *
     * @return
     */
    public abstract boolean isFree();

    public final RpcSvcStatus getStatus() {
        RpcSvcStatus status = onGetStatus();
        if (status == null) {
            //TODO throw exceptions
        }
        status.setIceProxyIdentity(getPrxIdentity());
        status.setFree(isFree());
        status.setIndex(svcIndex);
        // logger.debug(String.format("session status: %s", status), logTags);
        return status;
    }

    protected RpcSvcStatus onGetStatus() {
        return new RpcSvcStatus();
    }

    private int svcIndex;
    private IceEndpoint endpoint;

    public void setSvcIndex(int svcIndex) {
        this.svcIndex = svcIndex;
    }

    public void setEndpoint(IceEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public int getSvcIndex() {
        return this.svcIndex;
    }

    public IceEndpoint getIceEndpoint() {
        return this.endpoint;
    }

    public IceProxyIdentity getPrxIdentity() {
        return new IceProxyIdentity(this.getSvcName(), this.endpoint, this.svcIndex);
    }
}
