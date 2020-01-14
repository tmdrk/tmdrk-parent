package com.tmdrk.chat.common.entity.es.baseAnnotation;

import com.tmdrk.chat.common.entity.es.Default;

import java.lang.annotation.*;

/**
 * @ClassName ClassAnno
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/20 14:05
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClassAnno {
    Class<?> value() default Default.class; //
    boolean use() default false;  //是否使用
}