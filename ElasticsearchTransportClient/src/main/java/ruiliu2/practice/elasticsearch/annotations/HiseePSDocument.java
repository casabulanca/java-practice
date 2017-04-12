package ruiliu2.practice.elasticsearch.annotations;

import java.lang.annotation.*;

/**
 * 自定义注解用于标示用于ES操作的实体对象
 * Created by ruiliu2 on 2017/3/30.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface HiseePSDocument {

    /**
     * Elasticsearch索引名称
     *
     * @return indexName
     */
    String indexName();

    /**
     * Elasticsearch类型名称
     *
     * @return typeName
     */
    String typeName() default "";

}
