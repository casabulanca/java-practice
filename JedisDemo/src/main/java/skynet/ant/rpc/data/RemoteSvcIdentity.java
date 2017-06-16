package skynet.ant.rpc.data;

import com.alibaba.fastjson.annotation.JSONField;

import skynet.ant.core.domain.JsonableToString;
import skynet.ant.rpc.RpcHostSvcStatus;
import skynet.ant.rpc.ice.IceProxyIdentity;

/**
 * 远程服务标识
 * 
 * @author lyhu
 *
 */
public class RemoteSvcIdentity extends JsonableToString {

	public RemoteSvcIdentity() {

	}

	public RemoteSvcIdentity(IceProxyIdentity proxyIdentity, RpcHostSvcStatus rpcHostSvcStatus) {
		this.proxyIdentity = proxyIdentity;
		this.setRpcHostSvcStatus(rpcHostSvcStatus);

	}

	@JSONField(ordinal = 10, name = "host_proxy_identity")
	private IceProxyIdentity proxyIdentity;// 服务host代理标识

	@JSONField(ordinal = 20, name = "host_svc_status")
	private RpcHostSvcStatus rpcHostSvcStatus;// 服务状态

	public IceProxyIdentity getProxyIdentity() {
		return proxyIdentity;
	}

	public void setProxyIdentity(IceProxyIdentity proxyIdentity) {
		this.proxyIdentity = proxyIdentity;
	}

	public RpcHostSvcStatus getRpcHostSvcStatus() {
		return rpcHostSvcStatus;
	}

	public void setRpcHostSvcStatus(RpcHostSvcStatus rpcHostSvcStatus) {
		this.rpcHostSvcStatus = rpcHostSvcStatus;
	}
}