package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName Norms
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 21:25
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Norms {
    boolean value() default false; //
    boolean use() default false;   //是否使用
}
