package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName IgnoreAbove
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 22:00
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAbove {
    int value() default 100; //
    boolean use() default false;   //是否使用
}
