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
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;
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

    String stockAdd = "local res = {0}\n" +
            "      res[2] = redis.call('HINCRBY', KEYS[1],'Available',ARGV[1])\n" +
            "      res[3] = redis.call('HINCRBY', KEYS[1],'Lock',ARGV[2])\n" +
            "      if(res[2] < 0 or res[3] < 0)\n" +
            "      then\n" +
            "      local addNum = 0 - tonumber(ARGV[1])\n" +
            "      local lockNum = 0 - tonumber(ARGV[2])\n" +
            "      redis.call('HINCRBY', KEYS[1],'Available',addNum)\n" +
            "      redis.call('HINCRBY', KEYS[1],'Lock',lockNum)\n" +
            "      end\n" +
            "      if(res[2] < 0)\n" +
            "      then\n" +
            "      res[1] = res[1]-1\n" +
            "      end\n" +
            "      if(res[3] < 0)\n" +
            "      then\n" +
            "      res[1] = res[1]-2\n" +
            "      end\n" +
            "      return res";

    String stockBack = "local res = {0}\n" +
            "      local addNum = 0 - tonumber(ARGV[1])\n" +
            "      local lockNum = 0 - tonumber(ARGV[2])\n" +
            "      res[2] = redis.call('HINCRBY', KEYS[1],'Available',addNum)\n" +
            "      res[3] = redis.call('HINCRBY', KEYS[1],'Lock',lockNum)\n" +
            "      return res";

    String stockLock = "local res = {0,{-1,0},{-1,0},{-1,0},{-1,0}};\n" +
            "      local restStore = redis.call('HINCRBY', KEYS[1], 'couponStore', -ARGV[1]);\n" +
            "      res[2] = {restStore, -ARGV[1]};\n" +
            "      if(tonumber(restStore) < 0) then\n" +
            "        redis.call('HINCRBY', KEYS[1], 'couponStore', ARGV[1]);\n" +
            "        res[1] = 1;\n" +
            "        return res;\n" +
            "      end;\n" +
            "      local lockQuantity = redis.call('HINCRBY', KEYS[1], 'lockQuantity', ARGV[1]);\n" +
            "      res[3] = {lockQuantity, tonumber(ARGV[1])};\n" +
            "      return res;";

    /**
     * stockAdd 脚本执行 [0,0]
     * 第一次 [2,3]，  Available=2   Lock=3 返回[0,2,3]
     * 第二次 [2,3]，  Available=4   Lock=6 返回[0,4,6]
     * 第三次 [-3,-3]，Available=1   Lock=3 返回[0,1,3]
     * 第四次 [-3,-3]，Available=1   Lock=3 返回[-1,-2,0]
     * 第五次 [-3,-3]，Available=1   Lock=3 返回[-1,-2,0]
     * 第六次 [-1,-4]，Available=1   Lock=3 返回[-2,0,-1]
     * 第六次 [-3,-4]，Available=1   Lock=3 返回[-3,-2,-1]
     * 第七次 [-1,-3]，Available=0   Lock=0 返回[0,0,0]
     *
     * stockBack 脚本执行 [8,4]
     * 第一次 [3,2]，  Available=5   Lock=2  返回[0,5,2]
     * 第一次 [3,3]，  Available=2   Lock=-1 返回[0,2,-1]
     * 第一次 [3,3]，  Available=-1  Lock=-4 返回[0,-1,-4]
     */
    @Test
    public void redisStockAddTest() throws IOException, InterruptedException {
//        redisTemplate.delete("bargain:item");
        int addNum = 8;
        int lockNum = 0;
        byte[][] stockBytes = {
                "bargain:item".getBytes(), String.valueOf(addNum).getBytes(), String.valueOf(lockNum).getBytes()
        };
        RedisConnection connection = redisConnectionFactory.getConnection();
        // arg1:脚本; arg2:返回类型; arg3:key在入参中的长度; arg4:入参byte二维数组;
        ArrayList<Long> res = connection.eval(stockAdd.getBytes(), ReturnType.MULTI, 1, stockBytes);
        res.stream().forEach(System.out::println);
    }

    @Test
    public void redisStockBackTest() throws IOException, InterruptedException {
//        redisTemplate.delete("bargain:item");
        int addNum = 3;
        int lockNum = 3;
        byte[][] stockBytes = {
                "bargain:item".getBytes(), String.valueOf(addNum).getBytes(), String.valueOf(lockNum).getBytes()
        };
        RedisConnection connection = redisConnectionFactory.getConnection();
//        ArrayList<Long> res = connection.eval(stockBack.getBytes(), ReturnType.MULTI, 1, stockBytes);
        ArrayList<Long> res = connection.eval(stockBack.getBytes(), ReturnType.fromJavaType(List.class), 1, stockBytes);
        res.stream().forEach(System.out::println);
    }

    @Test
    public void redisStockLockTest() throws IOException, InterruptedException {
        int addNum = 3;
        byte[][] stockBytes = {
                "bargain:item".getBytes(), String.valueOf(addNum).getBytes()
        };
        RedisConnection connection = redisConnectionFactory.getConnection();
        ArrayList res = connection.eval(stockLock.getBytes(), ReturnType.fromJavaType(List.class), 1, stockBytes);
        res.forEach(a->{
            if(a instanceof Long){
                System.out.println(a);
            } else if(a instanceof List){
                ((List)a).stream().forEach(System.out::println);
            }else{
                System.out.println("a:"+a);
            }

        });
    }


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

//        String hscript = "local res = {0}\n" +
//                "local breakIndex = 0\n" +
//                "local argvLength = ARGV[1]\n" +
//                "local expire = tonumber(ARGV[2])\n" +
//                "for i = 1 , argvLength , 1 do\n" +
//                "    res[1+i] = redis.call('HINCRBY', KEYS[1], ARGV[2 + i], ARGV[2 + argvLength + i])\n" +
//                "    if res[1+i] < 0 then\n" +
//                "        breakIndex = i\n" +
//                "        break\n" +
//                "    end\n" +
//                "end\n" +
//                "if breakIndex > 0 then\n" +
//                "    res[1] = 0 - breakIndex\n" +
//                "    for i = 1 , breakIndex , 1 do\n" +
//                "        local addNum = 0 - tonumber(ARGV[2 + argvLength + i])\n" +
//                "        redis.call('HINCRBY', KEYS[1], ARGV[2 + i], addNum)\n" +
//                "    end\n" +
//                "end\n" +
//                "if res[1] == 0 then\n" +
//                "    if expire < 0 then\n" +
//                "        redis.call('PERSIST', KEYS[1])\n" +
//                "    elseif expire > 0 then\n" +
//                "        redis.call('EXPIRE', KEYS[1], expire)\n" +
//                "    end\n" +
//                "end\n" +
//                "return res";
//        connection.eval(hscript.getBytes(), ReturnType.fromJavaType(List.class), 1, null);
    }


}
