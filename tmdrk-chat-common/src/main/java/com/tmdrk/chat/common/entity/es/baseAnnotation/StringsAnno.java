package com.tmdrk.chat.common.entity.es.baseAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName StringsAnno
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/20 14:04
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StringsAnno {
    String[] value() default ""; //
    boolean use() default false; //是否使用
}