package ruiliu2.practice.JDKAnalysis.concurrency.aqs.interrupt;

import ruiliu2.practice.JDKAnalysis.concurrency.aqs.reentrantlock.ReentrantApplication;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by casa on 2017/6/15.
 */
public class InterruptApplication {

    private static ReentrantLock reentrantLock = new ReentrantLock(false);

    public static void main(String... args) throws InterruptedException {
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                for (; ; ) {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        System.out.println("interrupt");
                    }
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    for (; ; ) {

                    }
                } catch (Exception ex) {
                    System.out.println("thread1 interrupt");
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
