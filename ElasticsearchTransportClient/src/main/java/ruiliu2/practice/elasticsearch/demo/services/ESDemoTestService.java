package ruiliu2.practice.elasticsearch.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ruiliu2.practice.elasticsearch.core.DefaultElasticsearchRepo;
import ruiliu2.practice.elasticsearch.demo.entities.TestObject;
import ruiliu2.practice.elasticsearch.demo.entities.TransText;

/**
 * Created by casa on 2017/5/12.
 */
@Service
public class ESDemoTestService {

    @Autowired
    private DefaultElasticsearchRepo<TestObject> transTestDefaultElasticsearchRepo;

    public TestObject obj(TestObject obj) {
        return transTestDefaultElasticsearchRepo.create(obj);
    }

    public boolean index() {
        return transTestDefaultElasticsearchRepo.createIndex();
    }
}
