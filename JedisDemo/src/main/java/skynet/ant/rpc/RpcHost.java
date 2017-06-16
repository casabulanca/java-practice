package skynet.ant.rpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Strings;

import skynet.ant.core.AppContext;
import skynet.ant.core.config.IAntConfigService;
import skynet.ant.core.domain.JsonableToString;
import skynet.ant.core.logger.AntLogger;
import skynet.ant.core.service.IAntService;

/**
 * RPC 服务Host
 * 
 * Skynet AntService插件入口
 * 
 * @author lyhu
 *
 */
public final class RpcHost implements IAntService {
	private static final AntLogger logger = AntLogger.getLogger(RpcHost.class, AntLogger.platform);

	private static final String logTags = "rpc-host";

	@Autowired
	private IAntConfigService antConfigService;

	@Autowired
	private RpcHostContext rpcHostContext;

	@Autowired
	private RpcSvcContextDefault rpcSvcContextDefault;

	private List<String> nodeNames = new ArrayList<String>();

	@Override
	public String getSvcName() {
		return rpcHostContext.getHostSvcName();
	}

	@Override
	public final void run(AppContext appContext, Map<String, Object> paramContext) throws Exception {

		this.rpcSvcContextDefault.init(appContext, paramContext);
		this.rpcHostContext.init(appContext, this.rpcSvcContextDefault);
		this.rpcHostContext.listen();
		logger.info(String.format("report [%s] to zk.", this.rpcHostContext.getHostSvcName()), logTags);
		// 汇报节点状态
		nodeNames = appContext.getAntConfigService().reportSvcNode(rpcHostContext.getRpcHostStatus());
		logger.info(String.format("initialize ice service [%s] end.", this.rpcHostContext.getHostSvcName()), logTags);
	}

	/**
	 * 获取状态
	 *
	 * @return
	 */
	@Override
	public final Map<String, Object> getSvcState() {
		Map<String, Object> state = new HashMap<String, Object>();
		state.put(rpcHostContext.getHostSvcName(), rpcHostContext.getRpcHostSvcStatus());
		return state;
	}

	@Override
	public void close() throws Exception {
		antConfigService.cancelNode(nodeNames);
		if (this.rpcHostContext != null) {
			rpcHostContext.close();
		}
	}
}

class RpcSvcParam extends JsonableToString {

	@JSONField(ordinal = 10)
	public String protocol = "default";

	@JSONField(ordinal = 20)
	public int port = 0;

	@JSONField(name = "concurrency", ordinal = 25)
	public int concurrency = 1;

	@JSONField(ordinal = 30)
	public String svc;

	@JSONField(ordinal = 40)
	public String tag;

	protected String getRpcProtocol() {
		return Strings.isNullOrEmpty(protocol) ? "default" : protocol;
	}
}
