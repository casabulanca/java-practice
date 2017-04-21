package ruiliu2.practice.elasticsearch.annotations;

import java.lang.annotation.*;

/**
 * Elasticsearch字段属性
 * Created by ruiliu2 on 2017/4/13.
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Inherited
public @interface HiseePSField {

    HiseePSFieldType type() default HiseePSFieldType.Auto;

    HiseePSFieldIndex index() default HiseePSFieldIndex.not_analyzed;

    DateFormat format() default DateFormat.none;

    boolean store() default false;

    String search_analyzer() default "";

    String analyzer() default "";
}
