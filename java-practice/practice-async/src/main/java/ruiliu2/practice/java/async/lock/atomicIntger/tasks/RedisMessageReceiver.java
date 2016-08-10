package ruiliu2.practice.java.async.lock.atomicIntger.tasks;

import org.springframework.data.redis.connection.Message;
import redis.clients.jedis.JedisPubSub;

/**
 * redis message receiver
 * Created by ruiliu2@iflytek.com on 2016/8/11.
 */
public class RedisMessageReceiver extends JedisPubSub{

    public void onMessage(Message message, byte[] bytes) {

    }
}
