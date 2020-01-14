package com.tmdrk.chat.common.entity.es.analyzerAnnotation;

import com.tmdrk.chat.common.entity.es.Default;

import java.lang.annotation.*;

/**
 * @ClassName Analyzer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/20 11:38
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Analyzer {
    Class<?> value() default Default.class;
    boolean use() default false;   //是否使用
}
