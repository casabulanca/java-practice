package skynet.ant.rpc.session;

import java.util.UUID;

import com.alibaba.fastjson.JSON;

import Ice.Identity;
import skynet.ant.core.utils.OsUtil;
import skynet.ant.rpc.RpcHostContext;
import skynet.ant.rpc.RpcHostSvcStatus;
import skynet.ant.rpc.RpcProxy;
import skynet.ant.rpc.core.RpcParam;
import skynet.ant.rpc.core.RpcResponsePrx;
import skynet.ant.rpc.core.RpcResponsePrxHelper;
import skynet.ant.rpc.core.RpcStatus;
import skynet.ant.rpc.ice.IceProxyIdentity;

/**
 * Session会话请求代理。
 * 
 * 备注：根据identity 生成代理
 * 
 * @author lyhu
 *
 */
public class SessionReqProxy implements AutoCloseable {

	private RpcProxy rpcProxy;
	private RpcProxy rpcStatusProxy;
	private IceProxyIdentity iceProxyIdentity;
	private String from;
	private Ice.Communicator communicator;
	private Ice.ObjectAdapter adapter;

	/**
	 * 
	 * @param iceProxyIdentity
	 *            SimplePrinter:default -h 127.0.0.1 -p 2888
	 */
	public SessionReqProxy(IceProxyIdentity iceProxyIdentity) {
		this.iceProxyIdentity = iceProxyIdentity;
		this.from = OsUtil.getIPAddress();
	}

	/**
	 * 会话开始
	 * 
	 * @param trackId
	 * @param parameters
	 *            自定义参数
	 * @param context
	 *            会话自定义上下文
	 * @return 会话Id
	 **/
	public SessionStatus begin(String trackId, String parameters, byte[] context, SessionRespone sessionRespone) {

		RpcResponsePrx rpcResponsePrx = null;
		if (sessionRespone != null)
			rpcResponsePrx = getSessionResponsePrx(sessionRespone);

		RpcParam rpcParam = new RpcParam(trackId, parameters, from, context, rpcResponsePrx);
		RpcStatus rpcStatus = CurrentRpcProxy().getProxy().begin(rpcParam);
		return new SessionStatus(rpcStatus);
	}

	/**
	 * 会话进行中
	 * 
	 * @param sessionId
	 *            会话Id
	 * @param content
	 *            会话内容
	 * @return 会话状态码
	 **/
	public SessionStatus post(String sessionId, byte[] content) {

		RpcStatus rpcStatus = CurrentRpcProxy().getProxy().post(sessionId, content);
		return new SessionStatus(rpcStatus);
	}

	/**
	 * 会话结束
	 * 
	 * @param sessionId
	 *            会话Id
	 * @return 会话状态码
	 **/
	public SessionStatus end(String sessionId) {
		RpcStatus rpcStatus = CurrentRpcProxy().getProxy().end(sessionId);
		return new SessionStatus(rpcStatus);
	}

	public synchronized RpcHostSvcStatus getStatus(int timeout) {

		// 从HostContext 中获取状态
		if (rpcStatusProxy == null) {
			IceProxyIdentity contexProxyIdentity = new IceProxyIdentity(RpcHostContext.SvcName, iceProxyIdentity.getEndpoint());
			contexProxyIdentity.setTimeout(timeout);
			rpcStatusProxy = new RpcProxy(contexProxyIdentity);
		}

		byte[] statusBuffer = rpcStatusProxy.getProxy().call(from.getBytes());
		RpcHostSvcStatus status = JSON.parseObject(new String(statusBuffer), RpcHostSvcStatus.class);
		return status;
	}

	private synchronized RpcProxy CurrentRpcProxy() {
		if (this.rpcProxy == null)
			this.rpcProxy = new RpcProxy(iceProxyIdentity);
		return this.rpcProxy;
	}

	private RpcResponsePrx getSessionResponsePrx(SessionRespone sessionRespone) {
		communicator = CurrentRpcProxy().getCommunicator();
		adapter = communicator.createObjectAdapterWithEndpoints("Adapter_" + UUID.randomUUID(), "default");

		Identity identity = communicator.stringToIdentity("session_response_callback");
		adapter.add(sessionRespone, identity);
		adapter.activate();

		return RpcResponsePrxHelper.uncheckedCast(adapter.createProxy(identity));
	}

	@Override
	public void close() throws Exception {
		if (rpcProxy != null)
			rpcProxy.close();

		if (rpcStatusProxy != null)
			rpcStatusProxy.close();

		if (communicator != null)
			communicator.destroy();

		if (adapter != null)
			adapter.destroy();
	}
}
