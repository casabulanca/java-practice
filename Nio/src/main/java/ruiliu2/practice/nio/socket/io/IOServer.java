package ruiliu2.practice.nio.socket.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by casa on 2017/6/6.
 */
public class IOServer {

    private static ExecutorService tp = Executors.newCachedThreadPool();

    static class HandleMsg implements Runnable {
        Socket clientSocket;

        public HandleMsg(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            BufferedReader is = null;
            PrintWriter os = null;
            try {
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                os = new PrintWriter(clientSocket.getOutputStream(), true);
                //从InputStream当中读取客户端所发送的数据
                String inputLine = null;
                long b = System.currentTimeMillis();
                while ((inputLine = is.readLine()) != null) {
                    System.out.println(String.format("begin write, time: %s", System.currentTimeMillis()));
                    Thread.sleep(10000);
                    os.println(inputLine);
                }
                long e = System.currentTimeMillis();
                System.out.println(String.format("spend: %sms, time: %s", (e - b), System.currentTimeMillis()));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                    if (os != null) os.close();
                    clientSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static final int port = 9091;
    private ServerSocket socket;

    public IOServer() {
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) throws IOException {
        IOServer server = new IOServer();
        Socket clientSocket = null;
        while (true) {
            //实现每有一个客户端连接，启动一个线程去执行数据通信
            clientSocket = server.socket.accept();
            System.out.println(String.format("client connected, time: %s", System.currentTimeMillis()));
            tp.execute(new HandleMsg(clientSocket));
            System.out.println(String.format("dispose message, time: %s", System.currentTimeMillis()));
        }
    }
}
