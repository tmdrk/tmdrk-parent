package com.tmdrk.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author zhoujie
 * @Description
 *  1.引入springSecurity
 *  2.编写springSecurity配置
 *      @EnableWebSecurity
 *      public class MySecurityConfig extends WebSecurityConfigurerAdapter
 * @Date 1:28 2020/5/11
 * @Param
 * @return
 **/
@SpringBootApplication
public class TmdrkSpringbootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmdrkSpringbootSecurityApplication.class, args);
    }

}
