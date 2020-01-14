package com.tmdrk.chat.common.entity.es.baseAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName LongsAnno
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/20 13:54
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LongsAnno {
    long[] value() default 1; //
    boolean use() default false;  //是否使用
}
