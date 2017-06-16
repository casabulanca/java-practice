package skynet.ant.rpc.ice;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import skynet.ant.core.logger.AntLogger;
import skynet.ant.rpc.RpcSvcController;

/**
 * ice 服务容器
 * 
 * @author lyhu
 *
 */
public class IceCommunicator implements AutoCloseable {
	private static final AntLogger logger = AntLogger.getLogger(IceCommunicator.class, AntLogger.platform);
	private Ice.Communicator iceCommunicator = null;

	private Ice.ObjectAdapter iceObjectAdapter = null;

	private IceEndpoint endpoint = null;

	public IceCommunicator(IceEndpoint endpoint) {
		if (null == endpoint)
			throw new IllegalArgumentException("endpointParam");

		logger.debug("--Create Object Adapter With Endpoints..");

		iceCommunicator = Ice.Util.initialize(IceConfig.getInitializationData());

		iceObjectAdapter = iceCommunicator.createObjectAdapterWithEndpoints("Adapter_" + UUID.randomUUID().toString(), endpoint.toString());

		logger.debug(String.format("name:%s; endpoint:%s", iceObjectAdapter.getName(), StringUtils.join(iceObjectAdapter.getEndpoints())));

		this.endpoint = endpoint;
	}

	public IceEndpoint getEndpoint() {
		return endpoint;
	}

	public void add(RpcSvcController<?> servant) {
		logger.debug(String.format("add rpc service. [uri: %s]", servant.getPrxIdentity().getPrxIdentityUri()));
		iceObjectAdapter.add(servant, Ice.Util.stringToIdentity(servant.getPrxIdentity().getPrxIdentityId()));
	}

	/**
	 * 
	 */
	public void activate() {
		logger.debug(String.format("active servcie listen.[endpoint:%s]", endpoint));
		iceObjectAdapter.activate();
		// iceCommunicator.waitForShutdown();
	}

	public void close() throws Exception {
		logger.debug("IceCommunicator dispose");
		try {
			iceObjectAdapter.destroy();
			iceCommunicator.destroy();

		} catch (Ice.LocalException e) {
			e.printStackTrace();

		} catch (Exception e) {
			System.err.println(e.getMessage());

		} finally {
			if (iceCommunicator != null)
				iceCommunicator.destroy();
		}
	}
}
