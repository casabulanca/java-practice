package ruiliu2.practice.concurrency;

import sun.misc.Launcher;

import java.net.URL;

/**
 * Created by casa on 2017/6/3.
 */
public class SynchronizedApplication {

    public static void main(String... args) {
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
    }
}
