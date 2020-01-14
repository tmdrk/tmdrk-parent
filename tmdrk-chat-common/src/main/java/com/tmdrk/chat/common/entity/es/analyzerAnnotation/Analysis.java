package com.tmdrk.chat.common.entity.es.analyzerAnnotation;

import com.tmdrk.chat.common.entity.es.Default;

import java.lang.annotation.*;

/**
 * @ClassName Analysis
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/20 11:34
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Analysis {
    Class<?> value() default com.tmdrk.chat.common.entity.es.Analysis.class;
    boolean use() default false;   //是否使用
}
