package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName Boost
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 21:39
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Boost {
    float value() default 1; //
    boolean use() default false;   //是否使用
}
