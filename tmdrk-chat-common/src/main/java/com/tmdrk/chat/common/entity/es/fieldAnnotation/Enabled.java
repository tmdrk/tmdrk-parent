package com.tmdrk.chat.common.entity.es.fieldAnnotation;

/**
 * @ClassName Enabled
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 21:22
 * @Version 1.0
 **/
public @interface Enabled {
    boolean value() default true; //
    boolean use() default false;   //是否使用
}
