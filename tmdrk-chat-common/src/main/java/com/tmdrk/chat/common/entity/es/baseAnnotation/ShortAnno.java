package com.tmdrk.chat.common.entity.es.baseAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName ShortAnno
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/20 14:02
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShortAnno {
    short value() default 1; //
    boolean use() default false;  //是否使用
}