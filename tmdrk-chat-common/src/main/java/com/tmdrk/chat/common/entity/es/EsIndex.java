package com.tmdrk.chat.common.entity.es;

import java.lang.annotation.*;

/**
 * @ClassName EsIndex
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/18 15:10
 * @Version 1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EsIndex {
    Settings settings() default @Settings();
    boolean needMapping() default true;
    String[] aliases() default {};
}
