package skynet.ant.rpc.session;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Strings;

import skynet.ant.rpc.core.RpcStatus;

public class SessionStatus extends RpcStatus {

	private static final long serialVersionUID = -8522323272817077841L;

	public SessionStatus() {
	}

	public SessionStatus(String trackId, String sessionId, String errMsg) {
		this(trackId, sessionId, errMsg, null);
	}

	public SessionStatus(String trackId, String sessionId, String errMsg, Throwable throwable) {
		this(trackId, sessionId, new Exception(errMsg, throwable));
	}

	public SessionStatus(String trackId, String sessionId, Throwable ex) {
		super(trackId, sessionId, 1, null);
		this.Exception = ex;
		this.setStatus(ExceptionExt.GetMergedMessage(ex));
	}

	/**
	 * for debug
	 */
	@JSONField(serialize = false, deserialize = false)
	public Throwable Exception;

	public SessionStatus(String trackId, String sessionId) {
		super(trackId, sessionId, 0, null);
	}

	public SessionStatus(String trackId, String sessionId, int ok, String status) {
		super(trackId, sessionId, ok, status);
	}

	public SessionStatus(RpcStatus rpcStatus) {
		this(rpcStatus.trackId, rpcStatus.sessionId, rpcStatus.ok, rpcStatus.status);
	}

	public void setStatus(Object context) {

		if (context == null)
			return;
		this.status = JSON.toJSONString(context);
	}

	public <T> T getStatus(Class<T> clazz) {

		if (this.status == null)
			return null;
		String json = new String(this.status);
		if (Strings.isNullOrEmpty(json))
			return null;
		return JSON.parseObject(json, clazz);
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.PrettyFormat, SerializerFeature.SortField);
	}
}

class ExceptionExt {
	public static String GetMergedMessage(Throwable exp) {
		if (exp == null)
			return null;

		if (exp.getCause() == null) {
			return exp.getMessage();
		} else {
			return exp.getMessage() +

			GetMergedMessage(exp.getCause());
		}
	}
}
