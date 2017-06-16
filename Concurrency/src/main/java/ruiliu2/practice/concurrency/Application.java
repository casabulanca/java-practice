package ruiliu2.practice.concurrency;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by casa on 2017/6/3.
 */
public class Application {

    public static void main(String... args) throws InterruptedException, IOException {

        new Thread(new Runnable() {
            public synchronized void run() {
                for (int i = 0; i < 10; i = i + 2) {
                    if (i == 4) {
                        try {
                            Application.class.wait();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("thread 1 " + i);
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public synchronized void run() {
                for (int i = 1; i < 15; i = i + 2) {
                    if (i == 9) {
                        try {
                            Application.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("thread 2 " + i);
                    }
                }
            }
        }).start();

        Thread.sleep(1000);

        Application.class.notifyAll();
        System.out.println("notify");
    }
}
