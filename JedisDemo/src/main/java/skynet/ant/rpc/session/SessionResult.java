package skynet.ant.rpc.session;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

import skynet.ant.rpc.core.RpcResult;

/**
 * 会话结果
 * 
 * @author lyhu
 *
 */
public class SessionResult extends RpcResult {

	private static final long serialVersionUID = 1125297897321763769L;

	public SessionResult() {
	}

	public SessionResult(RpcResult result) {
		super(result.trackId, result.sessionId, result.result, result.context);
	}

	public SessionResult(String trackId, String sessionId, byte[] context) {
		super.trackId = trackId;
		super.sessionId = sessionId;
		super.context = context;
	}

	public void setResult(Object result) {
		this.result = _set(result);
	}

	public <T> T getResult(Class<T> clazz) {
		return _get(this.result, clazz);
	}

	private byte[] _set(Object data) {

		if (data == null)
			return null;

		String json = JSON.toJSONString(data);
		if (Strings.isNullOrEmpty(json))
			return null;
		return json.getBytes();
	}

	private <T> T _get(byte[] data, Class<T> clazz) {

		if (data == null)
			return null;
		String json = new String(data);
		if (Strings.isNullOrEmpty(json))
			return null;
		return JSON.parseObject(json, clazz);
	}
}
