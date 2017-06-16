package skynet.ant.rpc.ice;

import com.alibaba.fastjson.annotation.JSONField;

import skynet.ant.core.domain.JsonableToString;

/**
 * 代理标识<br/>
 * SimplePrinter:default -h 127.0.0.1 -p 2888
 * 
 * @author lyhu
 *
 */
public class IceProxyIdentity extends JsonableToString {

	@JSONField(ordinal = 10, name = "svc_index")
	private int svcIndex;

	@JSONField(ordinal = 20, name = "svc_name")
	private String svcName;
	
	

	@JSONField(ordinal = 30, name = "proxy_id")
	public String getPrxIdentityId() {
		if (this.svcIndex == 0)
			return this.svcName;
		else
			return String.format("%s_%s", this.svcName, svcIndex);
	}
	
	@JSONField(ordinal = 40)
	private int  timeout;
    
	

	/**
	 * SimplePrinter:default -h 127.0.0.1 -p 2888
	 */
	@JSONField(ordinal = 40, name = "proxy_uri")
	public String getPrxIdentityUri() {
		if (endpoint == null)
			return null;
		//为0使用默认超时
		if (timeout > 0){
		    return String.format("%s:%s -t %s", getPrxIdentityId(), endpoint.toString(),timeout);
		}else {
		    return String.format("%s:%s", getPrxIdentityId(), endpoint.toString());
		}
	}

	@JSONField(ordinal = 50)
	private IceEndpoint endpoint; 

	public IceProxyIdentity() {
	}

	public IceProxyIdentity(String svcName, IceEndpoint endPoint) {
		this(svcName, endPoint, 0);
	}

	public IceProxyIdentity(String svcName, IceEndpoint endPoint, int index) {
		this.setSvcName(svcName);
		this.setEndpoint(endPoint);
		this.setSvcIndex(index);
	}

	public int getSvcIndex() {
		return svcIndex;
	}

	public void setSvcIndex(int svcIndex) {
		this.svcIndex = svcIndex;
	}

	public String getSvcName() {
		return svcName;
	}

	public void setSvcName(String svcName) {
		this.svcName = svcName;
	}

	public IceEndpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(IceEndpoint endpoint) {
		this.endpoint = endpoint;
	}

	public void setTimeout(int value) {
        this.timeout = value;
    }
    
}
