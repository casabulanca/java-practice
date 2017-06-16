import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import elasticsearch.demo.entities.Lattice;
import ruiliu2.practice.jedisdemo.configuration.JedisConfiguration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ruiliu2 on 2017/4/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JedisConfiguration.class)
@Import(JedisConfiguration.class)
public class ConnectionTest {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void testInsert() {
        Jedis jedis = jedisPool.getResource();
        String key = jedis.set("test", "中华小当家");
        System.out.println(key);
        Assert.assertNotNull(key);
        jedis.close();
    }

    @Test
    public void testList() {
        Jedis jedis = jedisPool.getResource();
        String id = UUID.randomUUID().toString();
        long curry = new Date().getTime();

        System.out.println(String.format("cost: %s", new Date().getTime() - curry));
        jedis.close();
    }

    @Test
    public void testBinary() {

    }

    @Test
    public void testMap() {
        Jedis jedis = jedisPool.getResource();
        String id = UUID.randomUUID().toString();
        Map<String, String> map = new HashMap<String, String>();

        long current = new Date().getTime();
        jedis.hmset(id, map);
        System.out.println(new Date().getTime() - current);
    }

}
