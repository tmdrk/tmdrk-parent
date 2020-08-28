package com.tmdrk.myboot;

//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class TmdrkSpringbootMybootApplicationTests {
//
//    @Test
//    void contextLoads() {
//    }
//
//}

import com.tmdrk.myboot.redis.Result;
import com.tmdrk.myboot.redis.service.IRedisIncrService;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TmdrkSpringbootMybootApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Autowired
    IRedisIncrService redisIncrService;

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void contextLoads() {
        Logger logger = LoggerFactory.getLogger(TmdrkSpringbootMybootApplicationTests.class);
        //日志级别由低到高 trace<debug<info<warn<error
        //springboot默认输出info级别的日志
        //logging.level.com.tmdrk=debug
        logger.trace("这时{}日志...","trace");
        logger.debug("这时{}日志...","debug");
        logger.info("这时{}日志...","info");
        logger.warn("这时{}日志...","warn");
        logger.error("这时{}日志...","error");
    }

    @Test
    public void redisTest() throws IOException {
        System.out.printf("redis连接工厂：{%s}\n",redisTemplate.getConnectionFactory());
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println("name:"+name);

        RedisConnection connection = redisConnectionFactory.getConnection();
        System.out.println("redisConnectionFactory:"+redisConnectionFactory);
        System.out.println("connection:"+connection);
        System.out.println("redisIncrService:"+redisIncrService);

        System.out.println("redissonClient:"+redissonClient);

        RAtomicLong longObject = redissonClient.getAtomicLong("myLong"); //redis 没有，返回null
        System.out.println(longObject.get());
        RAtomicLong height = redissonClient.getAtomicLong("height");
        System.out.println(height.get());
        System.out.println(redissonClient.getConfig().toJSON());



        String key = "Key-activity-win:123";
        String total = "total";
        String use = "use";
        String surplus = "surplus";
//        RedisConnection conn = redisConnectionFactory.getConnection();
//        conn.hSet(key.getBytes(),total.getBytes(),"10".getBytes());
//        conn.hSet(key.getBytes(),surplus.getBytes(),"10".getBytes());


        Map<String, Long> incrMap = new HashMap<>();
        incrMap.put(use,2L);
        incrMap.put(surplus,-9L);
        Result<Map<String, Long>> result = redisIncrService.hincr(key, incrMap);
        System.out.println("successful:"+result.successful());
        Map<String, Long> data = result.getData();
        data.forEach((k,v)-> System.out.println("k:"+k+" v:"+v));

    }

}
