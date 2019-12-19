package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName NullValue
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 22:02
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NullValue {
    String value() default "NULL"; //
    boolean use() default false;   //是否使用
}
