package com.tmdrk.chat.common.entity.es.baseAnnotation;

import com.tmdrk.chat.common.entity.es.Default;

import java.lang.annotation.*;

/**
 * @ClassName ClassesAnno
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/20 14:06
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClassesAnno {
    Class<?>[] value() default Default.class; //
    boolean use() default false;  //是否使用
}