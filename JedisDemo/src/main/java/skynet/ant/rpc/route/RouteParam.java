package skynet.ant.rpc.route;

import skynet.ant.core.domain.JsonableToString;

/**
 * 路由请求参数<br/>
 * 主要包含 需要路由 服务名称（必选），tag（可选）；
 * 
 * @author lyhu
 *
 */
public class RouteParam extends JsonableToString {
	private String trackId;// 跟踪Id，一个是业务Id
	private String svcName;// 服务名称（必选）
	private String tag; // tag（可选）
	private String from;// 请求客户端IP

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSvcName() {
		return svcName;
	}

	public void setSvcName(String svnName) {
		this.svcName = svnName;
	}

}
