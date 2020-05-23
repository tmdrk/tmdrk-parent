package com.tmdrk.mycat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value={"com.tmdrk.mycat.mapper","com.tmdrk.mycat.annotationMapper"})
@SpringBootApplication
public class TmdrkSpringbootMycatApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmdrkSpringbootMycatApplication.class, args);
    }

}
