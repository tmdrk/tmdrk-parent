package com.tmdrk.myboot;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * BargainServiceTest
 *
 * @author Jie.Zhou
 * @date 2021/2/26 16:29
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BargainServiceTest {
    @Value("${bargain-bocf.script.checkAndBargainFixed}")
    private String checkAndBargainFixed;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Test
    public void initBargain(){

    }

    /**
     *
     *
     *
     *
     */
    @Test
    public void doBargain(){
        String key = "test:bargain:user-limit:";
        String activityId = "101";
        String customerId = "john lennon";

    }
}
