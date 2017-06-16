package skynet.ant.rpc;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Ice.Current;

import skynet.ant.core.AppContext;
import skynet.ant.core.logger.AntLogger;
import skynet.ant.rpc.core.RpcParam;
import skynet.ant.rpc.core.RpcStatus;
import skynet.ant.rpc.ice.IceCommunicator;
import skynet.ant.rpc.ice.IceEndpoint;

/**
 * RpcHost 上下文
 * <p/>
 * 同时又是 RpcSvcController 服务 ,此类服务比较特殊，隐藏在所有的Host中，
 * <p/>
 * 主要功能：
 * <ul>
 * <li>启动具体的 RpcSvc 服务（ RpcSvcContext + RpcSvcController ）</li>
 * <li>对外提供状态</li>
 * </ul>
 *
 * @author lyhu
 */
public final class RpcHostContext extends RpcSvcController<RpcSvcContextDefault> {

    private static final AntLogger logger = AntLogger.getLogger(RpcHost.class, AntLogger.platform);

    private static final String logTags = "rpc-host-ctx";
    public static final String SvcName = "skynet.rpc.host.context";

    private static final long serialVersionUID = 5876073132148020312L;

    private List<RpcSvcController<?>> hostRpcSvcControllerList = new ArrayList<RpcSvcController<?>>();
    private RpcSvcContext hostRpcSvcContext;

    private IceCommunicator iceCommunicator;
    private String hostSvcName;
    private RpcHostStatus rpcHostStatus;

    @Override
    public String getSvcName() {
        return SvcName;
    }

    public String getHostSvcName() {
        return hostSvcName;
    }

    /**
     * 获取Host本身的状态
     *
     * @return
     */
    public RpcHostStatus getRpcHostStatus() {
        return rpcHostStatus;
    }

    /**
     * 获取内部Host的服务状态列表
     *
     * @return
     */
    public RpcHostSvcStatus getRpcHostSvcStatus() {
        RpcHostSvcStatus status = new RpcHostSvcStatus();
        status.setHostStatus(rpcHostStatus);
        for (RpcSvcController<?> rpcSvcController : hostRpcSvcControllerList) {
            status.getServiceStatusList().add(rpcSvcController.getStatus());
        }
        return status;
    }

    public void init(AppContext appContext, RpcSvcContextDefault svcContext) throws Exception {

        logger.debug(String.format("initialize rpc service begin ..."));

        this.hostSvcName = svcContext.getRpcSvcParam().svc;
        super.setEndpoint(svcContext.getEndpoint());
        // 初始化上下文 ,可以没有
        String ctxBeanName = String.format("%s.context", svcContext.getRpcSvcParam().svc);
        if (appContext.getSpringContext().containsBean(ctxBeanName)) {
            this.hostRpcSvcContext = appContext.getSpringContext().getBean(ctxBeanName, RpcSvcContext.class);
            this.hostRpcSvcContext.init(appContext, svcContext.getParamContext());
        }

        // 多线程 并发初始化host service controller
        hostRpcSvcControllerList = getSvcControllerList(appContext, svcContext);
        logger.debug("initialize rpc service end", logTags);

        RpcHostStatus rpcHostStatus = new RpcHostStatus(appContext);
        rpcHostStatus.setSvcName(this.hostSvcName);
        rpcHostStatus.setEndpoint(svcContext.getEndpoint());
        rpcHostStatus.setTag(svcContext.getRpcSvcParam().tag);
        rpcHostStatus.setSvcContext(svcContext);
        this.rpcHostStatus = rpcHostStatus;
    }

    /**
     * 启动激活，对外开始进行侦听
     */
    public void listen() {
        logger.debug(String.format("active servcie listen.[endpoint:%s]", getIceEndpoint().toString()), logTags);
        iceCommunicator = new IceCommunicator(getIceEndpoint());
        iceCommunicator.add(this);
        for (RpcSvcController<?> rpcSvcController : hostRpcSvcControllerList) {
            iceCommunicator.add(rpcSvcController);
        }
        this.iceCommunicator.activate();
    }

    /**
     * 返回内部的状态
     */
    public final byte[] call(byte[] parameter, Current __current) {
        logger.debug(String.format("from:%s", new String(parameter)), logTags);
        RpcHostSvcStatus status = getRpcHostSvcStatus();
        return status.toString().getBytes(Charset.forName("utf-8"));
    }

    public void close() throws Exception {

        for (RpcSvcController<?> rpcSvcController : hostRpcSvcControllerList) {
            rpcSvcController.close();
        }
        hostRpcSvcControllerList.clear();

        if (iceCommunicator != null) {
            iceCommunicator.close();
            iceCommunicator = null;
        }

        if (this.hostRpcSvcContext != null) {
            this.hostRpcSvcContext.close();
            this.hostRpcSvcContext = null;
        }
    }

    private List<RpcSvcController<?>> getSvcControllerList(final AppContext appContext, RpcSvcContextDefault svcContext) {
        // 从0开始， 初始化 concurrency 路 rpcService 实例
        final RpcSvcParam rpcSvcParam = svcContext.getRpcSvcParam();
        final IceEndpoint ipendpoint = svcContext.getEndpoint();

        logger.debug(String.format("initialize rpc service [%s], concurrency:[%d] on port：%d.", rpcSvcParam.svc, rpcSvcParam.concurrency, ipendpoint.getPort()), logTags);

        List<RpcSvcController<?>> controllerList = new ArrayList<RpcSvcController<?>>();

        ExecutorService exec = Executors.newFixedThreadPool(8);
        CompletionService<RpcSvcController<?>> completionService = new ExecutorCompletionService<RpcSvcController<?>>(exec);
        // 并发 执行
        for (int i = 0; i < Math.max(rpcSvcParam.concurrency, 1); i++) {
            final int index = i;
            logger.debug(String.format("load the No.%d of [%s]..", index, rpcSvcParam.svc), logTags);
            completionService.submit(new Callable<RpcSvcController<?>>() {
                public RpcSvcController<?> call() throws Exception {
                    RpcSvcController<?> rpcSvcController = appContext.getSpringContext().getBean(String.format("%s.controller", rpcSvcParam.svc), RpcSvcController.class);
                    if (rpcSvcController == null) {
                        //TODO throw exceptions
                    }
                    rpcSvcController.setSvcIndex(index);
                    rpcSvcController.setEndpoint(ipendpoint);
                    rpcSvcController.init1(appContext, hostRpcSvcContext);
                    return rpcSvcController;
                }
            });
        }

        for (int i = 0; i < Math.max(rpcSvcParam.concurrency, 1); i++) {
            try {
                RpcSvcController<?> controller = completionService.take().get();
                if (controller != null) {
                    controllerList.add(controller);
                }
            } catch (InterruptedException e) {
                logger.error(String.format("get RpcSvcController error:%s", e.getMessage()), e, logTags);
            } catch (ExecutionException e) {
                logger.error(String.format("get RpcSvcController error:%s", e.getMessage()), e, logTags);
            }
        }
        exec.shutdown();

        // 按照 index 排序
        Collections.sort(controllerList, new Comparator<RpcSvcController<?>>() {
            public int compare(RpcSvcController<?> a1, RpcSvcController<?> a2) {
                return a1.getSvcIndex() > a2.getSvcIndex() ? 1 : -1;
            }
        });

        return controllerList;
    }

    public RpcStatus begin(RpcParam param, Current __current) {
        // TODO Auto-generated method stub
        return null;
    }

    public RpcStatus post(String sessionId, byte[] content, Current __current) {
        // TODO Auto-generated method stub
        return null;
    }

    public RpcStatus end(String sessionId, Current __current) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isFree() {
        return false;
    }

}
