package skynet.ant.rpc.route;

import java.util.List;

import skynet.ant.rpc.data.RemoteSvcIdentity;
import skynet.ant.rpc.ice.IceProxyIdentity;

/**
 * 获取最优路由策略
 * 
 * @author lyhu
 *
 */
public interface RoutePolicy {

	/**
	 * 获取最优服务
	 * 
	 * @param routeParam
	 *            路由参数
	 * @param onlineServiceList
	 *            在线服务节点列表，并且已经按照freeCout 倒序
	 * @return
	 */
	public IceProxyIdentity getRoute(RouteParam routeParam, List<RemoteSvcIdentity> onlineServiceList);
}
