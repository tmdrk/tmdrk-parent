package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName Dynamic
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 21:31
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dynamic {
    boolean value() default true; //
    boolean use() default false;   //是否使用
}
