package com.tmdrk.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value={"com.tmdrk.shiro.mapper"})
@SpringBootApplication
public class TmdrkSpringbootShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmdrkSpringbootShiroApplication.class, args);
    }

}
