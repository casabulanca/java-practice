package ruiliu2.practice.java.async.lock.atomicIntger.tasks;

import redis.clients.jedis.JedisPubSub;

/**
 * 消息消费者
 * Created by ruiliu2@iflytek.com on 16/8/17.
 */
public class MessageConsumerTask extends JedisPubSub {
    public void onMessage(String s, String s1) {
        // TODO: 16/8/17 获取同步锁进行消息消费
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
