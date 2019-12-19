package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName TremVector
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 22:41
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TremVector {
    String value() default "no"; //
    boolean use() default false;   //是否使用
}
