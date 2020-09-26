package com.tmdrk.myboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

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
    @Autowired
    private RedisConnectionFactory connectionFactory;

    public static void main(String[] args) {
        SpringApplication.run(TmdrkSpringbootMybootApplication.class, args);
    }

    @Bean
    public RedisTemplate<Serializable, ?> redisTemplate() {
        RedisTemplate<Serializable, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        // 设置 value 的转化格式和 key 的转化格式
        redisTemplate.setValueSerializer(new GenericToStringSerializer(Object.class));
        redisTemplate.setHashValueSerializer(new GenericToStringSerializer(Object.class));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
