package ruiliu2.practice.java.async.lock.atomicIntger.tasks;

import redis.clients.jedis.JedisPubSub;

/**
 * redis message receiver
 * Created by ruiliu2@iflytek.com on 2016/8/11.
 */
public class RedisMessageReceiver extends JedisPubSub {

    public void onMessage(String s, String s1) {

    }

    public void onPMessage(String s, String s1, String s2) {

    }

    public void onSubscribe(String s, int i) {

    }

    public void onUnsubscribe(String s, int i) {

    }

    public void onPUnsubscribe(String s, int i) {

    }

    public void onPSubscribe(String s, int i) {

    }
}
