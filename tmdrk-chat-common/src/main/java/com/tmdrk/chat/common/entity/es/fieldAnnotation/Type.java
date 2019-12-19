package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName Type
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/19 0:51
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Type {
    String value() default "keyword"; //
    boolean use() default true;   //是否使用
}
