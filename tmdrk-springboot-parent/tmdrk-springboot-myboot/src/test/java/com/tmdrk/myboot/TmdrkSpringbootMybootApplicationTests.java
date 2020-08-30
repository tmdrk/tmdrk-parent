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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmdrk.myboot.entity.Person;
import com.tmdrk.myboot.redis.Result;
import com.tmdrk.myboot.redis.service.IRedisIncrService;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    Redisson redisson;

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
    public void redisTest() throws IOException, InterruptedException {
        Person person = new Person();
        person.setAge(12);
        person.setBirth(new Date());
        person.setLastName("zhoujie");

        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        RedisSerializer keySerializer = redisTemplate.getKeySerializer();
        RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
        System.out.println("keySerializer:"+keySerializer);
        System.out.println("valueSerializer:"+valueSerializer);
        System.out.printf("redis连接工厂：{%s}\n",redisTemplate.getConnectionFactory());
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("myAge", "123");
        String myAge = (String)valueOperations.get("myAge");
        System.out.println("myAge: " + myAge);
        redisTemplate.opsForValue().set("name","zhoujie");
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println("name:"+name);
        //对象序列化
        redisTemplate.opsForValue().set("person",person);
        System.out.println("person:"+redisTemplate.opsForValue().get("person"));

        // redisTemplate

        RedisConnection connection1 = connectionFactory.getConnection();
        RedisConnection connection = redisConnectionFactory.getConnection();
        System.out.println("connectionFactory:"+connectionFactory);
        System.out.println("redisConnectionFactory:"+redisConnectionFactory);
        System.out.println("connection:"+connection);
//        connection.set("person1".getBytes(),);
        byte[] bytes = connection.get("name".getBytes());
        System.out.println("name::"+new String(bytes==null?"".getBytes():bytes));
        System.out.println("redisIncrService:"+redisIncrService);

        System.out.println("redissonClient:"+redissonClient);
        RBucket<Object> bucket = redissonClient.getBucket("test:codec");
        bucket.set("zhoujie");
        RAtomicLong longObject = redissonClient.getAtomicLong("myLong"); //redis 没有，返回null
        System.out.println(longObject.get());
        RAtomicLong height = redissonClient.getAtomicLong("height");
        System.out.println(height.get());
        System.out.println(redissonClient.getConfig().toJSON());
        //对象序列化
        RBucket<Object> person2 = redissonClient.getBucket("person2");
        person2.set(person);
        System.out.println("person2:"+person2.get());


        //测试redis lua脚本 redisConnectionFactory完成
        String key = "Key-activity-win:123";
        String total = "total";
        String use = "use";
        String surplus = "surplus";
        RedisConnection conn = redisConnectionFactory.getConnection();
        conn.set("test:codec1".getBytes(),"zhoujie".getBytes());
        conn.hSet(key.getBytes(),total.getBytes(),"10".getBytes());
        conn.hSet(key.getBytes(),surplus.getBytes(),"10".getBytes());

        //剩余库存(-)，已使用(+)
        Map<String, Long> incrMap = new HashMap<>();
        incrMap.put(use,2L);
        incrMap.put(surplus,-9L);
        Result<Map<String, Long>> result = redisIncrService.hincr(key, incrMap);
        System.out.println("successful:"+result.successful());
        Map<String, Long> data = result.getData();
        Optional.ofNullable(data).ifPresent((d)->d.forEach((k,v)-> System.out.println("k:"+k+" v:"+v)));


        RLock kky = redisson.getLock("kky");
        System.out.println(kky);
        try {
            if (kky.tryLock(1, 30, TimeUnit.SECONDS)) {
                System.out.println("成功拿到锁");
            }
        }finally{
            if(kky.isHeldByCurrentThread()){
                kky.unlock();
            }
        }

        redisTemplate.opsForValue().setBit("test:bitset:uv",23,true);
        redisTemplate.opsForValue().setBit("test:bitset:uv",13,true);
        redisTemplate.opsForValue().setBit("test:bitset:uv",2,true);
        Boolean aBoolean = redisTemplate.opsForValue().setBit("test:bitset:uv", 9, true);
        System.out.println("aBoolean:"+aBoolean);
        System.out.println("aBoolean:"+redisTemplate.opsForValue().getBit("test:bitset:uv", 9));
        System.out.println("aBoolean:"+redisTemplate.opsForValue().getBit("test:bitset:uv", 99));
        redisTemplate.opsForValue().setBit("test:bitset:uv",1000,true);
        redisTemplate.opsForValue().setBit("test:bitset:uv",1000000,true);
        System.out.println("test:bitset:uv->"+connection.bitCount("test:bitset:uv".getBytes()));
//        System.out.println("test:bitset:uv->"+connection.bitPos("test:bitset:uv".getBytes()));
    }

}
