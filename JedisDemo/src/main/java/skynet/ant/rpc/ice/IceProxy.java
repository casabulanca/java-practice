package skynet.ant.rpc.ice;

import skynet.ant.core.logger.AntLogger;

public abstract class IceProxy<IPrx extends Ice.ObjectPrx> implements AutoCloseable {
	private static final AntLogger logger = AntLogger.getLogger(IceProxy.class, AntLogger.platform);

	private String identUri;
	private IceEndpoint endpoint;
	private String svcName;
	private Ice.Communicator iceCommunicator = null;

	private IceProxyIdentity iceProxyIdentity;

	public IceProxy(IceProxyIdentity iceProxyIdentity) {
		this.iceProxyIdentity = iceProxyIdentity;
		this.endpoint = iceProxyIdentity.getEndpoint();
		this.svcName = iceProxyIdentity.getSvcName();
		this.identUri = iceProxyIdentity.getPrxIdentityUri(); 
	}

	public IceProxy(String svcName, IceEndpoint endPoint) {
		this(new IceProxyIdentity(svcName, endPoint));
	}

	public String getIpAddress() {
		return this.endpoint == null ? null : this.endpoint.getIpAddress();
	}

	public IceEndpoint getEndPoint() {
		return this.endpoint;
	}

	public String getSvcName() {
		return svcName;
	}

	public synchronized Ice.Communicator getCommunicator() {
		if (this.iceCommunicator == null)
			this.iceCommunicator = Ice.Util.initialize(IceConfig.getInitializationData());

		return iceCommunicator;
	}

	/**
	 * 获取 Ice Proxy Uri
	 * <p/>
	 * SimplePrinter:default -h 127.0.0.1 -p 2888
	 * 
	 * @return identUri
	 */
	public String getProxyIdentUri() {
		return iceProxyIdentity.toString();
	}

	private IPrx proxy = null;

	public IPrx getProxy() {
		if (proxy == null) {
			synchronized (this) {
				try {
				    logger.debug("connect url: " + identUri);
					Ice.ObjectPrx base = getCommunicator().stringToProxy(this.identUri);
					proxy = checkedCast(base);
				} catch (Ice.ConnectionRefusedException e) {
					logger.error(String.format("the uri not actived. uri: %s", this.identUri));
					throw e;
				}
				if (proxy == null) {
					logger.error(String.format("Invalid proxy. uri: %s", this.identUri));
					throw new Error(String.format("Invalid proxy. uri: %s", this.identUri));
				}
			}
		}
		return proxy;
	}

	public abstract IPrx checkedCast(Ice.ObjectPrx __obj);

	public void close() throws Exception {
		try {
			proxy = null;
			if (iceCommunicator != null) {
				iceCommunicator.destroy();
				iceCommunicator = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * SimplePrinter:default -h 127.0.0.1 -p 2888
	 * 
	 * @return
	 */
	public IceProxyIdentity getIceProxyIdentity() {
		return iceProxyIdentity;
	}

	@Override
	public String toString() {
		return this.getProxyIdentUri();
	}
}
