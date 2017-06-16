package ruiliu2.practice.elasticsearch.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ruiliu2.practice.elasticsearch.core.DefaultElasticsearchRepo;
import ruiliu2.practice.elasticsearch.core.query.SearchBody;
import ruiliu2.practice.elasticsearch.demo.entities.TransText;

import java.util.List;


/**
 * Created by ruiliu2 on 2017/4/20.
 */
@Service
public class ESDemoTextService {

    @Autowired
    private DefaultElasticsearchRepo<TransText> defaultElasticsearchRepo;

    public TransText entity(String id) {
        return defaultElasticsearchRepo.one(id);
    }

    public List<TransText> entities(SearchBody searchBody) {
        return defaultElasticsearchRepo.matchQueryPageData(searchBody);
    }

    public String update(TransText entity) {
        return defaultElasticsearchRepo.update(entity);
    }

    public String delete(String id) {
        return defaultElasticsearchRepo.delete(id);
    }

    public TransText entity(TransText transEntity) {
        return defaultElasticsearchRepo.create(transEntity);
    }

    public boolean index() {
        return defaultElasticsearchRepo.createIndex();
    }
}
