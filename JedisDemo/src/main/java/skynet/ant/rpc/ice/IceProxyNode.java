package skynet.ant.rpc.ice;

import skynet.ant.rpc.RpcHostStatus;

/**
 * IceProxyNode
 * 
 * 
 * @author lyhu
 *
 */
public class IceProxyNode<IPrx extends Ice.ObjectPrx, AppPrx extends IceProxy<IPrx>> implements AutoCloseable {

	public IceProxyNode(AppPrx appPrx, RpcHostStatus svcState) {
		this.appPrx = appPrx;
		this.svcState = svcState;
	}

	public RpcHostStatus getSvcState() {
		return svcState;
	}

	public AppPrx getAppPrx() {
		return appPrx;
	}

	private RpcHostStatus svcState;

	private AppPrx appPrx;

	@Override
	public void close() throws Exception {
		this.appPrx.close();
	}
}
