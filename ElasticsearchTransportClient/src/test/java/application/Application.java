package application;

import domain.TestDocument;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import ruiliu2.practice.elasticsearch.core.DefaultElasticsearchRepo;
import task.CreateTask;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 测试主程序入口
 * Created by ruiliu2 on 2017/4/10.
 */
public class Application {
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static TransportClient client;

    static {
        Settings settings = Settings.builder().put("cluster.name", "IntellijBladeMDF_Cluster").build();
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public static void main(String... args) throws ExecutionException, InterruptedException {
        DefaultElasticsearchRepo<TestDocument> repo = new DefaultElasticsearchRepo<>(client, TestDocument.class);
        System.out.println(String.format("Main Thread number: %s", Thread.currentThread().getId()));
        for (int i = 0; i < 50; i++) {
            Future<String> future = executorService.submit(new CreateTask(repo));
        }
        executorService.shutdown();
    }
}
