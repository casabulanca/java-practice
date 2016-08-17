package ruiliu2.practice.java.configuration.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * redis configuration
 * Created by ruiliu2@iflytek.com on 16/1/18.
 */
@Configuration
public class RedisConfiguration {


    /**
     * 装配Jedis Pool
     *
     * @return Jedis Pool
     */
    @Bean
    public JedisPool jedisPool() {

        String host = "127.0.0.1";
        int port = 6379;

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(50);
        config.setMinIdle(0);
        config.setTestOnBorrow(true);

        return new JedisPool(config, host, port);
    }
}
