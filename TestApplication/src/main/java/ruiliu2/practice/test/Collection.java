package ruiliu2.practice.test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;


/**
 * Created by casa on 2017/6/8.
 */
public class Collection {

    private static ReentrantLock lock = new ReentrantLock(false);
    private static ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
    private static Map<String, Object> hashMap = new HashMap<>();
    private static List<String> arrayList = new ArrayList<>();
    private static int[] origin = new int[100];
    private static int[] dest = new int[50];

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String... args) {


        for (Map.Entry item : hashMap.entrySet()) {
            item.getKey();
            item.getValue();

            synchronized (Collection.class) {
                try {
                    Collection.class.wait();
                } catch (InterruptedException ex) {

                }
            }
        }

        try {
            RandomAccessFile file = new RandomAccessFile("path", "rw");
            while (file.read() != -1) {

            }
        } catch (IOException ex) {

        }

        System.arraycopy(origin, 0, dest, 1, 10);


        String test = "1231231";

    }
}
