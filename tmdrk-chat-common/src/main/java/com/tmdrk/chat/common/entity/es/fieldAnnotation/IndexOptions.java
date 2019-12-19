package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import com.tmdrk.chat.common.entity.es.IndexOption;

import java.lang.annotation.*;

/**
 * @ClassName IndexOptions
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 21:24
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IndexOptions {
    IndexOption value() default IndexOption.docs;
    boolean use() default false;   //是否使用
}
