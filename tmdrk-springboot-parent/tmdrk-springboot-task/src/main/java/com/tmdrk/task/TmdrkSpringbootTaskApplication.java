package com.tmdrk.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //开启定时任务
@EnableAsync  //开启异步处理
@SpringBootApplication
public class TmdrkSpringbootTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmdrkSpringbootTaskApplication.class, args);
    }

}
