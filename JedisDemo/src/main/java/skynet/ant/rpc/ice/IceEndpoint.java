package skynet.ant.rpc.ice;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Strings;

/**
 * Ice 服务IPEndPoint参数
 * 
 * @author lyhu
 *
 */
public class IceEndpoint {

	@JSONField(name = "ip", ordinal = 10)
	private String ipAddress;

	@JSONField(name = "port", ordinal = 20)
	private int port;

	@JSONField(name = "protocol", ordinal = 30)
	private String protocol = "default";

	public IceEndpoint() {
		protocol = "default";
	}

	public IceEndpoint(String ip, int port) {
		this();
		this.ipAddress = ip;
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {

		// default -h 127.0.0.1 -p 2888
		return String.format("%s -h %s -p %d", Strings.isNullOrEmpty(protocol) ? "default" : protocol, ipAddress, port);
	}

}
