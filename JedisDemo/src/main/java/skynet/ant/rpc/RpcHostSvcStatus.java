package skynet.ant.rpc;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import skynet.ant.core.domain.JsonableToString;

/**
 * rpc host 服务 状态
 * 
 * 包括：hostStatus、serviceStatusList
 * 
 * @author lyhu
 *
 */
public class RpcHostSvcStatus extends JsonableToString {
	public RpcHostSvcStatus() {
		serviceStatusList = new ArrayList<RpcSvcStatus>();
	}

	@JSONField(ordinal = 10, name = "host_status")
	private RpcHostStatus hostStatus;

	@JSONField(ordinal = 20, name = "service_list")
	private List<RpcSvcStatus> serviceStatusList;

	@JSONField(ordinal = 15, name = "free_num")
	public int getFreeNum() {
		if (serviceStatusList == null)
			return 0;
		int num = 0;
		for (RpcSvcStatus rpcSvcStatus : serviceStatusList) {
			if (rpcSvcStatus.isFree()) {
				num++;
			}
		}
		return num;
	}

	public RpcHostStatus getHostStatus() {
		return hostStatus;
	}

	public void setHostStatus(RpcHostStatus hostStatus) {
		this.hostStatus = hostStatus;
	}

	public List<RpcSvcStatus> getServiceStatusList() {
		return serviceStatusList;
	}

	public void setServiceStatusList(List<RpcSvcStatus> serviceStatusList) {
		this.serviceStatusList = serviceStatusList;
	}

}
