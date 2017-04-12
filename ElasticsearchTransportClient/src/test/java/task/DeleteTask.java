package task;

import domain.TestDocument;
import ruiliu2.practice.elasticsearch.core.DefaultElasticsearchRepo;

import java.util.concurrent.Callable;

/**
 * Created by ruiliu2 on 2017/4/10.
 */
public class DeleteTask implements Callable<String> {

    private DefaultElasticsearchRepo<TestDocument> repo;

    public DeleteTask(DefaultElasticsearchRepo<TestDocument> repo) {
        this.repo = repo;
    }

    @Override
    public String call() throws Exception {
        return null;
    }
}
