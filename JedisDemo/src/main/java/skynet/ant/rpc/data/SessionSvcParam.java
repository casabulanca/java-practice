package skynet.ant.rpc.data;

import com.alibaba.fastjson.annotation.JSONField;

import skynet.ant.core.domain.JsonableToString;

/**
 * Session 服务 参数
 * 
 * @author lyhu
 *
 */
public class SessionSvcParam extends JsonableToString {

	@JSONField(name = "tag", ordinal = 10)
	public String tag;

	@JSONField(name = "concurrency", ordinal = 20)
	public int concurrency;

	@JSONField(name = "timeout_post_ms", ordinal = 30)
	public int postTimeOutMs = 5000;

	@JSONField(name = "timeout_end_ms", ordinal = 40)
	public int endTimeOutMs = 2000;

}
