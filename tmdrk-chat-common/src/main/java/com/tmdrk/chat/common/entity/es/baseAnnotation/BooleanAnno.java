package com.tmdrk.chat.common.entity.es.baseAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName BooleanAnno
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/20 14:07
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BooleanAnno {
    boolean value() default true; //
    boolean use() default false;  //是否使用
}