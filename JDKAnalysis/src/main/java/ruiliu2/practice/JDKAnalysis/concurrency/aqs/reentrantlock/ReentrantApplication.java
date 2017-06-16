package ruiliu2.practice.JDKAnalysis.concurrency.aqs.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by casa on 2017/6/15.
 */
public class ReentrantApplication {

    private static ReentrantLock reentrantLock = new ReentrantLock(false);

    public static void main(String... args) throws InterruptedException {
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                for (; ; ) {
                    SleepUtil.sleep(5);
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                for (; ; ) {

                }
            }
        });

        thread.start();
        thread1.start();

//        TimeUnit.SECONDS.sleep(5);

        thread.interrupt();
        thread1.interrupt();

        System.out.println(thread.isInterrupted());
        System.out.println(thread1.isInterrupted());

//        reentrantLock.lock();
//
//        reentrantLock.unlock();
//
//        reentrantLock.tryLock();
//
//        reentrantLock.tryLock(10, TimeUnit.SECONDS);
//
//        reentrantLock.lockInterruptibly();
    }

    public static class SleepUtil {


        public static void sleep(long time) {
            try {
                TimeUnit.SECONDS.sleep(time);
            } catch (InterruptedException e) {

            }
        }
    }
}
