package ruiliu2.practice.test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 *
 */
public class ThreadPool {

    private Queue<Runnable> blockQueue = new ConcurrentLinkedQueue<>();

    private ReentrantLock lock = new ReentrantLock(false);

    private Condition condition = lock.newCondition();

    public volatile boolean running = true;

    public ThreadPool() {
        innerLoop();
    }

    /**
     * 实际处理
     */
    private void innerLoop() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    try {
                        lock.lock();
                        while (blockQueue.isEmpty()) {
                            condition.await();
                        }
                        Runnable task = blockQueue.poll();

                        new Thread(task).start();
                    } catch (InterruptedException ex) {
                        //log
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }).start();


    }

    public void addTask(Runnable task) {
        try {
            lock.lock();

            this.blockQueue.add(task);

            condition.signalAll();
        } catch (Exception ex) {
            //log
        } finally {
            lock.unlock();
        }
    }

    public static void main(String... args) {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 10; i++) {

            final int finalI = i;
            threadPool.addTask(new Runnable() {

                @Override
                public void run() {
                    System.out.println("do work" + finalI);
                }
            });
        }
    }


}
