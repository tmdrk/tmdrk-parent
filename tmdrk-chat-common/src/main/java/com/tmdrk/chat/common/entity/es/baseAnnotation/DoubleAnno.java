package com.tmdrk.chat.common.entity.es.baseAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName DoubleAnno
 * @Description
 * @Author zhoujie
 * @Date 2019/12/20 13:56
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoubleAnno {
    double value() default 1; //
    boolean use() default false;  //是否使用
}
