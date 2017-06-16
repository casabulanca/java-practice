package skynet.ant.rpc;

import Ice.ObjectPrx;
import skynet.ant.rpc.core.RpcRequestPrx;
import skynet.ant.rpc.core.RpcRequestPrxHelper;
import skynet.ant.rpc.ice.IceEndpoint;
import skynet.ant.rpc.ice.IceProxy;
import skynet.ant.rpc.ice.IceProxyIdentity;

public class RpcProxy extends IceProxy<RpcRequestPrx> {

	public RpcProxy(IceProxyIdentity iceProxyIdentity) {
		super(iceProxyIdentity);
	}

	public RpcProxy(String svcName, IceEndpoint endPoint) {
		this(new IceProxyIdentity(svcName, endPoint));
	}

	@Override
	public RpcRequestPrx checkedCast(ObjectPrx __obj) {
		return RpcRequestPrxHelper.checkedCast(__obj);
	}
}