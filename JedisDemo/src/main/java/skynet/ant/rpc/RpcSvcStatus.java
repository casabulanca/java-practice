package skynet.ant.rpc;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import skynet.ant.core.domain.JsonableToString;
import skynet.ant.rpc.ice.IceProxyIdentity;

/**
 * Rpc 服务状态
 * 
 * VO
 * 
 * @author lyhu
 *
 */
public class RpcSvcStatus extends JsonableToString {

	public RpcSvcStatus() {
		this.setTime(new Date());
	}

	/**
	 * 状态时间
	 */
	@JSONField(ordinal = 10, format = "yyyy-MM-dd HH:mm:s.SSS")
	private Date time;

	@JSONField(ordinal = 15)
	private int index;

	/**
	 * 代理标识
	 */
	@JSONField(ordinal = 20, name = "proxy_identity")
	private IceProxyIdentity iceProxyIdentity;

	@JSONField(ordinal = 30)
	private boolean isFree;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public IceProxyIdentity getIceProxyIdentity() {
		return iceProxyIdentity;
	}

	public RpcSvcStatus setIceProxyIdentity(IceProxyIdentity iceProxyIdentity) {
		this.iceProxyIdentity = iceProxyIdentity;
		return this;
	}

	public boolean isFree() {
		return isFree;
	}

	public RpcSvcStatus setFree(boolean isFree) {
		this.isFree = isFree;
		return this;
	}

}
