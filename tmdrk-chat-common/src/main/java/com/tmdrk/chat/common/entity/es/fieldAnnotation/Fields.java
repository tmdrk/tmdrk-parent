package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import com.tmdrk.chat.common.entity.es.Properties;

import java.lang.annotation.*;

/**
 * @ClassName Fields
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 21:39
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Fields {
    Class<?> value(); //
    boolean use() default false;   //是否使用
}
