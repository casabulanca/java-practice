package ruiliu2.practice.nio.socket.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by casa on 2017/6/6.
 */
public class IOClient {

    private static final long sleep = 1000 * 1000 * 1000L;

    public static void main(String... args) {
        PrintWriter printWriter = null;
        BufferedReader bf = null;
        Socket socket = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("138.68.63.99", 9091));
            printWriter = new PrintWriter(socket.getOutputStream());
            bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter.write("h");
            System.out.println(String.format("send h, time: %s", System.currentTimeMillis()));
            LockSupport.parkNanos(sleep);
            printWriter.write("e");
            System.out.println(String.format("send e, time: %s", System.currentTimeMillis()));
            LockSupport.parkNanos(sleep);
            printWriter.write("l");
            System.out.println(String.format("send l, time: %s", System.currentTimeMillis()));
            LockSupport.parkNanos(sleep);
            printWriter.write("l");
            System.out.println(String.format("send l, time: %s", System.currentTimeMillis()));
            LockSupport.parkNanos(sleep);
            printWriter.write("o");
            System.out.println(String.format("send o, time: %s", System.currentTimeMillis()));
            LockSupport.parkNanos(sleep);
            printWriter.println();
            printWriter.flush();
            System.out.println(String.format("flush , time: %s", System.currentTimeMillis()));

            System.out.println(String.format("from server: %s", bf.readLine()));
        } catch (IOException io) {

        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }

                if (printWriter != null) {
                    printWriter.close();
                }

                if (bf != null) {
                    bf.close();
                }
            } catch (IOException io) {

            }
        }
    }
}
