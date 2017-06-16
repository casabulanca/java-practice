package skynet.ant.rpc.route;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import skynet.ant.rpc.core.RpcStatus;
import skynet.ant.rpc.ice.IceProxyIdentity;

/**
 * 路由结果
 * 
 * @author lyhu
 *
 */
public class RouteResult extends RpcStatus {

	private static final long serialVersionUID = -7698900051876178643L;

	public RouteResult() {
	}

	public RouteResult(String trackId) {
		super(trackId, null, 0, null);
	}

	public RouteResult(String trackId, String errMsg) {
		this(trackId, errMsg, null);
	}

	public RouteResult(String trackId, String errMsg, Throwable throwable) {
		this(trackId, new Exception(errMsg, throwable));
	}

	public RouteResult(String trackId, Throwable ex) {
		super(trackId, null, 1, ExceptionExt.GetMergedMessage(ex));
	}

	@JSONField(ordinal = 10, name = "proxy_identity")
	private IceProxyIdentity proxyIdentity;// 最优的服务代理标识

	public IceProxyIdentity getProxyIdentity() {
		return proxyIdentity;
	}

	public void setProxyIdentity(IceProxyIdentity proxyIdentity) {
		this.proxyIdentity = proxyIdentity;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.PrettyFormat, SerializerFeature.SortField);
	}
}

class ExceptionExt {
	public static String GetMergedMessage(Throwable exp) {
		if (exp == null)
			return null;

		if (exp.getCause() == null) {
			return exp.getMessage();
		} else {
			return exp.getMessage() +

			GetMergedMessage(exp.getCause());
		}
	}
}