package ruiliu2.practice.JDKAnalysis.concurrency.lock;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by casa on 2017/6/12.
 */
public class LockApplication {

    private static ReentrantLock lock = new ReentrantLock(false);
    static Condition condition = lock.newCondition();


    public static void main(String... args) {
        try {
            lock.lock();
            condition.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
