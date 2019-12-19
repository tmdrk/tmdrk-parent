package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName EsIndex
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 20:46
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Index {
    boolean value() default true; //
    boolean use() default false;   //是否使用
}
