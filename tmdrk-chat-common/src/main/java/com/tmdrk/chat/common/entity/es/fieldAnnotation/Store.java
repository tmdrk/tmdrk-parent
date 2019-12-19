package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName Store
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 21:30
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Store {
    boolean value() default false; //
    boolean use() default false;   //是否使用
}
