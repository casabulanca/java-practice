package ruiliu2.practice.elasticsearch.core;


import ruiliu2.practice.elasticsearch.core.query.Pagination;

import java.util.List;

/**
 * Elasticsearch操作接口约定
 * Created by ruiliu2 on 2017/3/30.
 */
public interface ElasticsearchOperations<T> {

    T create(T instance);

    String delete(String id);

    String update(T instance);

    List<T> matchQueryPageData(String fieldName, String searchBody, Pagination pagination);
}
