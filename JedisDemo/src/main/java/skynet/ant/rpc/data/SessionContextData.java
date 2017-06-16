package skynet.ant.rpc.data;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import skynet.ant.core.domain.JsonableToString;
import skynet.ant.rpc.core.RpcParam;

/**
 * Session 服务 会话上下文
 * 
 * VO
 * 
 * @author lyhu
 */
public class SessionContextData extends JsonableToString {

	@JSONField(name = "session_id", ordinal = 10)
	private String sessionId;

	@JSONField(name = "start_time", format = "yyyy-MM-dd HH:mm:ss", ordinal = 20)
	private Date startTime;

	@JSONField(name = "elapsed_time", ordinal = 30)
	private String lapsedTime = null;

	@JSONField(name = "session_param", ordinal = 40)
	private RpcParam rpcParam;

	/**
	 * 会话回调地址
	 */
	@JSONField(name = "callback_proxy", ordinal = 35)
	private String callbackProxy;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public RpcParam getRpcParam() {
		return rpcParam;
	}

	public void setRpcParam(RpcParam rpcParam) {
		this.rpcParam = rpcParam;
	}

	public String getLapsedTime() {
		return lapsedTime;
	}

	public void setLapsedTime(String lapsedTime) {
		this.lapsedTime = lapsedTime;
	}

	public String getCallbackProxy() {
		return callbackProxy;
	}

	public void setCallbackProxy(String callbackProxy) {
		this.callbackProxy = callbackProxy;
	}

}