package com.tmdrk.chat.common.entity.es.fieldAnnotation;

import java.lang.annotation.*;

/**
 * @ClassName SearchAnalyzer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 22:40
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SearchAnalyzer {
    String value() default "standard"; //
    boolean use() default false;   //是否使用
}
