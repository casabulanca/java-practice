package application;

import com.alibaba.fastjson.JSON;
import domain.JSONContent;
import domain.TestDocument;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import ruiliu2.practice.elasticsearch.core.DefaultElasticsearchRepo;
import ruiliu2.practice.elasticsearch.core.query.Pagination;
import ruiliu2.practice.elasticsearch.demo.entities.Lattice;
import ruiliu2.practice.elasticsearch.demo.entities.TransEntity;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
//        TestDocument testDocument = new TestDocument();
//        List<JSONContent> contents = new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < 1000; i++) {
//            String text = "你好世界杯";
//            JSONContent jsonContent = new JSONContent();
//            jsonContent.setBeginTime(1000);
//            jsonContent.setEndTime(2000);
//            jsonContent.setId(UUID.randomUUID().toString());
//            jsonContent.setOnebestText(text);
//            jsonContent.setWs(text);
//            contents.add(jsonContent);
//            sb.append(text);
//        }
//        testDocument.setContents(contents);
//        testDocument.setRes_id(UUID.randomUUID().toString());
//        testDocument.setContent(sb.toString());
//
//        repo.create(testDocument);
//        Pagination pagination = new Pagination.PaginationBuilder().pageNumber(0).pageSize(10).build();


//        System.out.println(JSON.toJSONString(repo.matchQueryPageData("content", "世界杯", pagination)));

//        repo.createIndex();
    }
}
