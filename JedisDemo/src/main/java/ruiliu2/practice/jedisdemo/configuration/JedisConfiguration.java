package ruiliu2.practice.jedisdemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by ruiliu2 on 2017/4/24.
 */
@Configuration
public class JedisConfiguration {

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1024);
        config.setMaxIdle(10);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        return new JedisPool(config, "192.168.75.172", 6379, 2000);
    }
}
