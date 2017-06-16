package skynet.ant.rpc.session;

import java.util.Map;

import com.alibaba.fastjson.JSON;

import skynet.ant.core.AppContext;
import skynet.ant.core.logger.AntLogger;
import skynet.ant.rpc.RpcSvcContext;
import skynet.ant.rpc.data.SessionSvcParam;

public abstract class SessionSvcContext implements RpcSvcContext {

	private final static String logTags = "session-svc-ctx";

	private static final AntLogger logger = AntLogger.getLogger(SessionSvcContext.class, AntLogger.platform);

	private SessionSvcParam sessionSvcParam;

	protected abstract void onInit(AppContext appContext, Map<String, Object> paramContext) throws Exception;

	@Override
	public final void init(AppContext appContext, Map<String, Object> paramContext) throws Exception {
		String svcContextJson = JSON.toJSONString(paramContext);
		logger.info(String.format("service context: %s", svcContextJson), logTags);

		this.sessionSvcParam = JSON.parseObject(svcContextJson, SessionSvcParam.class);

		this.onInit(appContext, paramContext);
	}

	public SessionSvcParam getSessionSvcParam() {
		return sessionSvcParam;
	}

}
