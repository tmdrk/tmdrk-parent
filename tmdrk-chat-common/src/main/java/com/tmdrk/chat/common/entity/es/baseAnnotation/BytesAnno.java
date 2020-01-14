package com.tmdrk.chat.common.entity.es.baseAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName BytesAnno
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/20 13:58
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BytesAnno {
    byte[] value() default 1; //
    boolean use() default false;  //是否使用
}
