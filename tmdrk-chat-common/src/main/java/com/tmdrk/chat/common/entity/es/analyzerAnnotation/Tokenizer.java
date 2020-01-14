package com.tmdrk.chat.common.entity.es.analyzerAnnotation;

import com.tmdrk.chat.common.entity.es.Default;
import com.tmdrk.chat.common.entity.es.baseAnnotation.StringAnno;

import java.lang.annotation.*;

/**
 * @ClassName Tokenizer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/20 11:47
 * @Version 1.0
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Tokenizer {
    Class<?> value() default Default.class;
    boolean use() default false;   //是否使用
}
