package skynet.ant.rpc.data;

import com.alibaba.fastjson.annotation.JSONField;

import skynet.ant.core.domain.JsonableToString;
import skynet.ant.rpc.route.RoutePolicy4Default;

public class RouteSvcParam extends JsonableToString {

	@JSONField(name = "policyName", ordinal = 10)
	public String policyName = RoutePolicy4Default.PolicyName;

}
