package com.tmdrk.myboot.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName HelloCommandLineRunner
 * @Description
 * @Author zhoujie
 * @Date 2020/5/9 19:25
 * @Version 1.0
 **/
@Component
public class HelloCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("HelloCommandLineRunner.run..."+args);
    }
}
