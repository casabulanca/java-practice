package skynet.ant.rpc;

import skynet.ant.rpc.core.RpcRequestPrx;
import skynet.ant.rpc.ice.IceEndpoint;
import skynet.ant.rpc.ice.IceProxyManager;

/**
 * RequestProxyManager
 * 
 * 
 * 
 * @author lyhu
 *
 */

public class RpcProxyManager extends IceProxyManager<RpcRequestPrx, RpcProxy> {

	@Override
	protected RpcProxy getAppPrx(String svcId, IceEndpoint endPoint) {
		return new RpcProxy(svcId, endPoint);
	}
}