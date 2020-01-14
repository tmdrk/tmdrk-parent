package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName Analyzer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 21:33
 * @Version 1.0
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Analyzer {
    String value() default "standard"; //
    boolean use() default false;   //是否使用
}
