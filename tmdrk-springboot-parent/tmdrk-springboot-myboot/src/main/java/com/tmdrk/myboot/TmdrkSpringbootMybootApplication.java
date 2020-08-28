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
        redisTemplate.opsForValue().set("spring-r-cluster-1", 123);
        redisTemplate.opsForValue().set("spring-r-cluster-2", 456);
        redisTemplate.opsForValue().set("spring-r-cluster-3", 789);
    }

    public static void main(String[] args) {
        SpringApplication.run(TmdrkSpringbootMybootApplication.class, args);
    }

}
