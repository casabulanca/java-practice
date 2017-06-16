package skynet.ant.rpc;

import com.alibaba.fastjson.annotation.JSONField;

import skynet.ant.core.AppContext;
import skynet.ant.core.domain.AntServiceState;
import skynet.ant.rpc.ice.IceEndpoint;
import skynet.ant.rpc.ice.IceProxyIdentity;

/**
 * rpc host 状态
 * 
 * 主要是静态状态
 * 
 * @author lyhu
 *
 */
public class RpcHostStatus extends AntServiceState {

	/**
	 * 获取 Ice Proxy Uri
	 * <p/>
	 * SimplePrinter:default -h 127.0.0.1 -p 2888
	 * 
	 * @return
	 */
	@JSONField(name = "proxy_uri", ordinal = 100)
	public String getProxyUri() {
		return this.getIceProxyIdentity().getPrxIdentityUri();
	}

	@JSONField(serialize = false)
	public IceProxyIdentity getIceProxyIdentity() {
		return new IceProxyIdentity(this.getSvcName(), this.endpoint);
	}

	/**
	 * 标签<br/>
	 * 后期可以考虑放到 AntServiceState中 by lyhu
	 */
	@JSONField(name = "tag", ordinal = 300)
	private String tag;

	@JSONField(name = "endpoint", ordinal = 400)
	private IceEndpoint endpoint;

	public RpcHostStatus() {

	}

	public RpcHostStatus(AppContext appContext) {
		super(appContext);

	}

	public IceEndpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(IceEndpoint endpoint) {
		this.endpoint = endpoint;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}