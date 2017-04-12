package ruiliu2.practice.annotations;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * 测试配置
 * Created by ruiliu2 on 2017/3/31.
 */
@Component
@Import(DemoService.class)
public class DemoConfig {

}
