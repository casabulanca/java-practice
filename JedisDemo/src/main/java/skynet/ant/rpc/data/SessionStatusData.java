package skynet.ant.rpc.data;

import com.alibaba.fastjson.annotation.JSONField;

import skynet.ant.rpc.RpcSvcStatus;

/**
 * Session 服务 状态(一路会话状态):
 * 
 * VO
 * 
 * 服务状态，正在会话上下文
 * 
 * @author lyhu
 *
 */
public class SessionStatusData extends RpcSvcStatus {

	public SessionStatusData() {
		this.serviceParam = new SessionSvcParam();
	}

	@JSONField(ordinal = 200, name = "service_param")
	private SessionSvcParam serviceParam;

	@JSONField(ordinal = 300, name = "session_context")
	private SessionContextData sessionContext;

	public SessionSvcParam getServiceParam() {
		return serviceParam;
	}

	public void setServiceParam(SessionSvcParam serviceParam) {
		this.serviceParam = serviceParam;
	}

	public SessionContextData getSessionContext() {
		return sessionContext;
	}

	public void setSessionContext(SessionContextData sessionContext) {
		this.sessionContext = sessionContext;
	}

}
