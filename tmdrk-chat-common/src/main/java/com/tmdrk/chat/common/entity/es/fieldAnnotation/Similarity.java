package com.tmdrk.chat.common.entity.es.fieldAnnotation;

/**
 * @ClassName Similarity
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 22:41
 * @Version 1.0
 **/
public @interface Similarity {
    String value() default "BM25"; //
    boolean use() default false;   //是否使用
}
