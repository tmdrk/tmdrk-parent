package com.tmdrk.chat.common.entity.es;

import com.tmdrk.chat.common.entity.es.baseAnnotation.ClassAnno;

import java.lang.annotation.*;

/**
 * @ClassName Settings
 * @Description 索引settings设置
 * @Author zhoujie
 * @Date 2019/12/18 15:11
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Settings {
    int numberOfShards() default 2;
    int numberOfReplicas() default 1;

    String analysis() default "";
}
