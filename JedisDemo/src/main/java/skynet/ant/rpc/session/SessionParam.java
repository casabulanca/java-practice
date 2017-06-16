package skynet.ant.rpc.session;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

import skynet.ant.rpc.core.RpcParam;

public class SessionParam extends RpcParam {

	private static final long serialVersionUID = 6656288864098362048L;

	public SessionParam() {

	}

	public SessionParam(String sessionId, RpcParam RpcParam) {
		super(RpcParam.trackId, RpcParam.parameters, RpcParam.from, RpcParam.context, RpcParam.callbackProxy);
		this.sessionId = sessionId;

	}

	private String sessionId;

	public void setContext(Object context) {

		if (context == null)
			return;

		String json = JSON.toJSONString(context);
		if (Strings.isNullOrEmpty(json))
			return;
		this.context = json.getBytes();
	}

	public <T> T getContext(Class<T> clazz) {

		if (this.context == null)
			return null;
		String json = new String(this.context);
		if (Strings.isNullOrEmpty(json))
			return null;
		return JSON.parseObject(json, clazz);
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
