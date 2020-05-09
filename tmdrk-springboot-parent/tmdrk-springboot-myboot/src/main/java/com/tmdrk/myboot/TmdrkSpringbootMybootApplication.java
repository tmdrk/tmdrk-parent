package com.tmdrk.myboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @Author zhoujie
 * @Description
 *  SpringApplication.run(TmdrkSpringbootMybootApplication.class, args);
 *      return new SpringApplication(primarySources).run(args);
 *          1
 *
 * @Date 2:18 2020/5/9
 * @Param
 * @return
 **/
@SpringBootApplication
public class TmdrkSpringbootMybootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmdrkSpringbootMybootApplication.class, args);
    }

}
