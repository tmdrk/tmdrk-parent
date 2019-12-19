package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName Fielddata
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 21:28
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Fielddata {
    boolean value() default false; //
    boolean use() default false;   //是否使用
}
