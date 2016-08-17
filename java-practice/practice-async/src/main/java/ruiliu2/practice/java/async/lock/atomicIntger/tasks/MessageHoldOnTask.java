package ruiliu2.practice.java.async.lock.atomicIntger.tasks;

import redis.clients.jedis.JedisPubSub;

/**
 * 消息僵持者
 * Created by ruiliu2@iflytek.com on 16/8/17.
 */
public class MessageHoldOnTask extends JedisPubSub {

    public void onMessage(String s, String s1) {
        // TODO: 16/8/17 开启一个线程进行睡眠
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
