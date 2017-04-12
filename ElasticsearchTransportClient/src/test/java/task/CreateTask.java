package task;

import domain.TestDocument;
import ruiliu2.practice.elasticsearch.core.ElasticsearchOperations;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * 创建index测试任务
 * Created by ruiliu2 on 2017/4/10.
 */
public class CreateTask implements Callable<String> {

    private ElasticsearchOperations<TestDocument> elasticsearchRepo;

    public CreateTask(ElasticsearchOperations<TestDocument> elasticsearchRepo) {
        this.elasticsearchRepo = elasticsearchRepo;
    }

    @Override
    public String call() throws Exception {
        TestDocument testDocument = new TestDocument();
        testDocument.setRes_id(UUID.randomUUID().toString());
        testDocument.setContent("你好世界杯");
        String res_id = elasticsearchRepo.create(testDocument).getRes_id();
        System.out.println(String.format("Thread number: %s, res_id: %s", Thread.currentThread().getId(), res_id));
        return res_id;
    }
}
