package com.tmdrk.chat.common.entity.es.analyzerAnnotation;

import com.tmdrk.chat.common.entity.es.Default;

import java.lang.annotation.*;

/**
 * @ClassName Filter
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/20 11:37
 * @Version 1.0
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Filter {
    Class<?> value() default Default.class;
    boolean use() default false;   //是否使用
}
