package com.tmdrk.chat.common.entity.es;

import java.lang.annotation.*;

/**
 * @ClassName Settings
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 15:11
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Settings {
    int numberOfShards() default 2;
    int numberOfReplicas() default 1;
}
