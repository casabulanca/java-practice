package skynet.ant.rpc;

import java.util.Map;

import skynet.ant.core.AppContext;

/**
 * 会话服务 上下文
 * 
 * 与 RpcSvcController 配对使用
 * 
 * 在spring中 单例
 * 
 * @author lyhu
 *
 */
public interface RpcSvcContext extends AutoCloseable {

	public void init(AppContext appContext, Map<String, Object> paramContext) throws Exception;
}
