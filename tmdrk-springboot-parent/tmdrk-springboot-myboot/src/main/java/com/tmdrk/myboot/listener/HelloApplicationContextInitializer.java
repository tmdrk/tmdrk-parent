package com.tmdrk.myboot.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ClassName HelloApplicationContextInitializer
 * @Description
 * @Author zhoujie
 * @Date 2020/5/9 19:23
 * @Version 1.0
 **/
public class HelloApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("HelloApplicationContextInitializer.initialize..."+applicationContext);
    }
}
