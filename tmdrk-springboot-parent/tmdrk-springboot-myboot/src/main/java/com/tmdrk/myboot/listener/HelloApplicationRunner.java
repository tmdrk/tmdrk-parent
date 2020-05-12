package com.tmdrk.myboot.listener;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName HelloApplicationRunner
 * @Description
 * @Author zhoujie
 * @Date 2020/5/9 19:24
 * @Version 1.0
 **/
@Component
public class HelloApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("HelloApplicationRunner.run..."+args);
    }
}
