package ruiliu2.practice.elasticsearch.core;


import ruiliu2.practice.elasticsearch.core.query.Pagination;
import ruiliu2.practice.elasticsearch.core.query.SearchBody;

import java.util.List;

/**
 * Elasticsearch操作接口约定
 * Created by ruiliu2 on 2017/3/30.
 */
public interface ElasticsearchDocumentOperations<T> {

    T create(T instance);

    String delete(String id);

    String update(T instance);

    List<T> matchQueryPageData(SearchBody searchBody);

    List<T> all(Pagination pagination);

    T one(String id);
}
