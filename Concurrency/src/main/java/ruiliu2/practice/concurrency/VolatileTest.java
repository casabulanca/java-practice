package ruiliu2.practice.concurrency;

/**
 * Created by casa on 2017/6/5.
 */
public class VolatileTest extends Thread {
    boolean keepRunning = true;

    public void run() {
        long count = 0;
        while (keepRunning) {
//            if (keepRunning){
////                System.out.println(111);
//            }
            count++;
        }

        System.out.println("Thread terminated." + count);
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileTest t = new VolatileTest();
        t.start();
        Thread.sleep(1000);
        t.keepRunning = false;
        System.out.println("keepRunning set to false.");
    }
}
