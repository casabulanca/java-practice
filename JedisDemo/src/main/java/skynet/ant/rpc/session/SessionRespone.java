package skynet.ant.rpc.session;

import Ice.Current;
import skynet.ant.rpc.core.RpcResult;
import skynet.ant.rpc.core.RpcStatus;
import skynet.ant.rpc.core._RpcResponseDisp;

/**
 * 会话响应
 * 
 * 实现具体会话逻辑
 * 
 * @author lyhu
 *
 */
public abstract class SessionRespone extends _RpcResponseDisp {

	private static final long serialVersionUID = 8815533667762977009L;

	public abstract SessionStatus onCallback(SessionResult sessionResult);

	@Override
	public RpcStatus callback(RpcResult result, Current __current) {
		return onCallback(new SessionResult(result));
	}
}
