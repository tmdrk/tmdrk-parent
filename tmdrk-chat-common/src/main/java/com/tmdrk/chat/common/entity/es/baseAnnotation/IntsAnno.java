package com.tmdrk.chat.common.entity.es.baseAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName IntsAnno
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/20 13:53
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IntsAnno {
    int[] value() default 1; //
    boolean use() default false;  //是否使用
}