package ruiliu2.practice.elasticsearch.core;

/**
 * ES index操作抽象
 * Created by ruiliu2 on 2017/4/13.
 */
public interface ElasticsearchIndexOperations<T> {

    boolean createIndex();
}
