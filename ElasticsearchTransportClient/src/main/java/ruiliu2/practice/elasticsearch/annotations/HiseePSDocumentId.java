package ruiliu2.practice.elasticsearch.annotations;

import java.lang.annotation.*;

/**
 * Created by casa on 2017/4/1.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HiseePSDocumentId {
}
