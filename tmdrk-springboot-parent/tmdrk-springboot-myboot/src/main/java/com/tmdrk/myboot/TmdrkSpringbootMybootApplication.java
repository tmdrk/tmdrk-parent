package com.tmdrk.myboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

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
    public TmdrkSpringbootMybootApplication(RedisTemplate redisTemplate) {

    }

    public static void main(String[] args) {
        SpringApplication.run(TmdrkSpringbootMybootApplication.class, args);
    }

}
