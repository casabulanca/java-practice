package skynet.ant.rpc.session;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;

import Ice.Current;
import skynet.ant.core.AppContext;
import skynet.ant.core.logger.AntLogger;
import skynet.ant.core.utils.ManualResetEvent;
import skynet.ant.rpc.RpcSvcController;
import skynet.ant.rpc.RpcSvcStatus;
import skynet.ant.rpc.core.RpcParam;
import skynet.ant.rpc.core.RpcStatus;
import skynet.ant.rpc.data.SessionContextData;
import skynet.ant.rpc.data.SessionStatusData;

/**
 * 会话服务处理器
 * <p/>
 * SessionService 一个实例 对应 一路并发
 *
 * @author lyhu
 */
public abstract class SessionSvcController<T extends SessionSvcContext> extends RpcSvcController<T> {

    private static final long serialVersionUID = 7613543287816670482L;
    private final static String logTags = "session-svc-ctrl";

    private static final AntLogger logger = AntLogger.getLogger(SessionSvcController.class, AntLogger.platform);

    protected abstract void onInit(AppContext appContext, T svcContext) throws Exception;

    protected abstract SessionStatus onBegin(SessionParam sessionParam, Current rpcCtx);

    protected abstract SessionStatus onPost(String trackId, String sessionId, byte[] content, Current rpcCtx);

    protected abstract SessionStatus onEnd(String trackId, String sessionId, Current rpcCtx);

    protected abstract void onTimeout(String trackId, String sessionId) throws Exception;

    private SessionContext sessionContext;

    private T svcContext;

    private AtomicLong counter = new AtomicLong();

    /**
     * // timeout checker 信号量
     */
    private ManualResetEvent manualResetEvent = new ManualResetEvent(false);

    /**
     * 初始化
     */
    @Override
    public void init(AppContext appContext, T svcContext) throws Exception {
        this.svcContext = svcContext;
        this.onInit(appContext, svcContext);

        // timeout checker 信号量
        this.manualResetEvent = new ManualResetEvent(false);
        this.checkSessionTimeout(this.svcContext.getSessionSvcParam().postTimeOutMs, manualResetEvent);
        counter.set(0);
    }

    public final RpcStatus begin(RpcParam rpcParam, Current __current) {

        if (rpcParam == null)
            throw new IllegalArgumentException("sessionServices begin the [rpcParam] is null.");
        logger.debug(rpcParam.trackId, String.format("begin rpc session, param:. %s", rpcParam), logTags);

        if (sessionContext != null) {
            String msg = String.format("current servcie is busy");
            logger.info(rpcParam.trackId, msg, logTags);
            return new SessionStatus(rpcParam.trackId, null, msg);
        }

        sessionContext = new SessionContext(rpcParam);
        SessionStatus sessionStatus = null;
        try {
            SessionParam sessionParam = new SessionParam(sessionContext.getSessionId(), rpcParam);
            sessionStatus = this.onBegin(sessionParam, __current);
            sessionStatus.sessionId = sessionContext.getSessionId();
            sessionStatus.trackId = rpcParam.trackId;

        } catch (Exception e) {
            sessionStatus = new SessionStatus(rpcParam.trackId, sessionContext.getSessionId(), String.format("call onBegin error:%s", e.getMessage()), e);
        }

        logger.debug(rpcParam.trackId, String.format("[begin] return RpcStatus:%s", sessionStatus), logTags);
        // 启动 timeout checker
        this.manualResetEvent.set();
        return sessionStatus;
    }

    public final RpcStatus post(String sessionId, byte[] content, Current __current) {

        if (Strings.isNullOrEmpty(sessionId))
            throw new IllegalArgumentException("sessionServices post the [sessionId] is empty.");
        if (content == null || content.length == 0)
            throw new IllegalArgumentException("sessionServices post the [content] is null or empty.");

        if (sessionContext == null || !sessionId.equals(sessionContext.getSessionId())) {
            String msg = String.format("the sessionId [%s] is not begin or timeout end", sessionId);
            logger.info(msg, logTags);
            return new SessionStatus(null, sessionId, msg);
        }

        String trackId = sessionContext.getRpcParam().trackId;

        if (counter.getAndIncrement() % 100 == 0)
            logger.debug(trackId, String.format("post session id:%s, content size: %d", sessionId, content.length), logTags);

        sessionContext.trigger();

        SessionStatus sessionStatus = null;
        try {
            sessionStatus = this.onPost(trackId, sessionId, content, __current);
            sessionStatus.sessionId = sessionId;
            sessionStatus.trackId = trackId;
        } catch (Exception e) {
            sessionStatus = new SessionStatus(trackId, sessionId, String.format("call onPost error:%s", e.getMessage()), e);
        }

        // logger.debug(trackId, String.format("[post] return RpcStatus:%s",
        // sessionStatus), logTags);

        return sessionStatus;
    }

    public final RpcStatus end(String sessionId, Current __current) {
        if (Strings.isNullOrEmpty(sessionId))
            throw new IllegalArgumentException("sessionServices end the [sessionId] is empty.");

        if (sessionContext == null || !sessionId.equals(sessionContext.getSessionId())) {
            String msg = String.format("the sessionId [%s] is not begin or timeout end", sessionId);
            logger.info(msg, logTags);
            return new SessionStatus(null, sessionId, msg);
        }

        String trackId = sessionContext.getRpcParam().trackId;
        logger.debug(trackId, String.format("end session id:%s", sessionId), logTags);
        sessionContext.trigger();

        // 调用结束就 关闭 timeout checker
        this.manualResetEvent.reset();

        SessionStatus sessionStatus = null;
        try {
            sessionStatus = this.onEnd(trackId, sessionId, __current);
            sessionStatus.sessionId = sessionId;
            sessionStatus.trackId = trackId;
        } catch (Exception e) {
            sessionStatus = new SessionStatus(trackId, sessionId, String.format("call onEnd error:%s", e.getMessage()), e);
        }
        // 置为空闲
        sessionContext = null;
        counter.set(0);
        logger.debug(trackId, String.format("[end] return RpcStatus:%s", sessionStatus), logTags);

        return sessionStatus;
    }

    /**
     * 是否空闲
     *
     * @return
     */
    @Override
    public final boolean isFree() {
        return sessionContext == null;
    }

    /**
     * 返回内部的状态
     */
    public final byte[] call(byte[] parameter, Current __current) {
        logger.debug(String.format("from:%s", new String(parameter)), logTags);
        RpcSvcStatus status = getStatus();
        return status.toString().getBytes(Charset.forName("utf-8"));
    }

    /**
     * 获取服务内部状态
     *
     * @return
     */
    @Override
    protected RpcSvcStatus onGetStatus() {

        SessionStatusData sessionStatusData = new SessionStatusData();
        sessionStatusData.setServiceParam(this.svcContext.getSessionSvcParam());

        if (sessionContext != null) {
            SessionContextData ctx = JSON.parseObject(JSON.toJSONString(sessionContext), SessionContextData.class);
            sessionStatusData.setSessionContext(ctx);
        }

        // logger.debug(String.format("session status: %s",
        // sessionStatusData.toString()), logTags);

        return sessionStatusData;
    }

    public void close() throws Exception {
        // TODO:

    }

    private void checkSessionTimeout(final int timeout_ms, final ManualResetEvent manualResetEvent) {
        if (timeout_ms <= 0)
            return;
        logger.info(String.format("the session timeout checker is starting [timeout:%s ms]", timeout_ms), logTags);
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    // 当没有会话时 减少check 频率
                    try {
                        // manualResetEvent.waitOne(timeout_ms << 4);
                        manualResetEvent.waitOne();
                        // sleep 超时时间
                        Thread.sleep(timeout_ms);
                        logger.debug(String.format("the timeout session ...[timeout:%s ms]", timeout_ms), logTags);

                        if (sessionContext != null && sessionContext.isTimeout(timeout_ms)) {
                            String msg = String.format("end timeout session id:%s", sessionContext.getSessionId());
                            logger.debug(msg, logTags + "-timeout");
                            System.err.println(msg);
                            try {
                                onTimeout(sessionContext.getRpcParam().trackId, sessionContext.getSessionId());
                            } catch (Exception e) {
                                logger.error(msg + " on end error.", e, logTags + "-timeout-error");
                            } finally {
                                // 将 sessionContext 强制置空
                                sessionContext = null;
                                // 关闭 timeout checker
                                manualResetEvent.reset();
                            }
                        }
                    } catch (Exception e) {
                        logger.error(String.format("check session error:", e.getMessage()), e, logTags);
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.setName(String.format("checkSessionTimeoutThread.pid:%d", Thread.currentThread().getId()));
        thread.start();
    }

    @Override
    public String getSvcName() {
        return null;
    }

}

/**
 * Session 服务 会话上下文,此类不可逆初始化
 * <p/>
 * 生成 SessionId
 *
 * @author lyhu
 */
class SessionContext {

    public SessionContext(RpcParam rpcParam) {
        this.sessionId = UUID.randomUUID().toString();
        this.rpcParam = rpcParam;
        this.pulse = Stopwatch.createStarted();
        this.startTime = new Date();
        this.elapsedTime = Stopwatch.createStarted();
    }

    @JSONField(name = "session_id", ordinal = 10)
    private String sessionId;

    @JSONField(name = "start_time", ordinal = 20)
    private Date startTime;

    @JSONField(name = "elapsed_time", ordinal = 30)
    private Stopwatch elapsedTime = null;

    @JSONField(name = "session_param", ordinal = 40)
    private RpcParam rpcParam;

    /**
     * 会话回调地址
     * <p/>
     * 不便于修改 RpcParam 中的 callbackProxy 属性，再次多加一个属性 对外提供。
     *
     * @return
     */
    @JSONField(name = "callback_proxy", ordinal = 35)
    public String callbackProxy() {
        return (rpcParam != null && rpcParam.callbackProxy != null) ? rpcParam.callbackProxy.toString() : null;
    }

    private Stopwatch pulse = null;

    /**
     * 触发一下，防止过期
     */
    public void trigger() {
        pulse.reset().start();
    }

    public String getElapsedTime() {
        return elapsedTime.toString();
    }

    /**
     * 是否超时
     *
     * @param timeout_ms 毫秒
     * @return
     */
    public boolean isTimeout(int timeout_ms) {
        return (pulse.elapsed(TimeUnit.MILLISECONDS) >= timeout_ms);
    }

    public String getSessionId() {
        return sessionId;
    }

    public RpcParam getRpcParam() {
        return rpcParam;
    }

    public Date getStartTime() {
        return startTime;
    }
}
