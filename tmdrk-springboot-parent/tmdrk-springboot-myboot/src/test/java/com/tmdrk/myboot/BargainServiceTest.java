package com.tmdrk.myboot;

import com.tmdrk.myboot.redis.Result;
import com.tmdrk.myboot.redis.service.IRedisIncrService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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

    @Autowired
    IRedisIncrService redisIncrService;

    @Test
    public void initBargain() {

    }

    /**
     *
     */
    @Test
    public void doBargain() {
        String keyPrefix = "test:bargain:user-limit:";
        String activityId = "101";
        String customerId = "john lennon";
        String limitKey = keyPrefix + "{" + activityId + "}" + customerId;
        String recordKey = "test:bargain:record:" + "{" + activityId + "}" + "1";
        for (int i = 0; i < 10; i++) {
            redisTemplate.delete(limitKey);

            redisTemplate.delete(recordKey);
            // 初始化数据
            Random r = new Random();
            Map<String, Long> incrMap = new HashMap<>();

            incrMap.put("surplusAmt", 1000L + r.nextInt(3000));//剩余金额
            incrMap.put("newCnt", 0L);//新会员数
            incrMap.put("newCardCnt", 0L);//新开卡数
            incrMap.put("bargainPrice", 0L);//底价金额
            incrMap.put("bargainedPrice", 0L);//已砍金额
            incrMap.put("status", 0L);//砍价状态
//            long total = 1L+r.nextInt(10);
//            incrMap.put("surplusCnt",total);//剩余人数
//            incrMap.put("totalCnt",total);//总人数

//            incrMap.put("surplusAmt",2L);//剩余金额
//            incrMap.put("surplusCnt",3L);//剩余人数
//            incrMap.put("totalCnt",3L);//总人数

//            incrMap.put("surplusAmt",4000L);//剩余金额
//            incrMap.put("newCnt",0L);//新会员帮砍人数
//            incrMap.put("newCustomerCnt",0L);//新客户帮砍人数
//            incrMap.put("oldCustomerCnt",0L);//老客户帮砍人数


            Result<Map<String, Long>> result = redisIncrService.hincr(recordKey, incrMap);
            System.out.println("successful:" + result.successful());
            Map<String, Long> data = result.getData();
            Optional.ofNullable(data).ifPresent((d) -> d.forEach((k, v) -> System.out.println("k:" + k + " v:" + v)));

            // 执行脚本
            RedisConnection connection = redisConnectionFactory.getConnection();
            // arg1:脚本; arg2:返回类型; arg3:key在入参中的长度,即前几个是入参; arg4:入参byte二维数组;
            for (int j = 0; j < 10; j++) {
                ArrayList res;
                int type = 1; // 1 随机金额 2 固定金额 3 平均金额

                boolean newMem = isNewMem();
                Random rand = new Random();
                int firstType = rand.nextInt(3);
                int amt;
                if (newMem) {
                    amt = getRandom(10, 10);
                } else {
                    amt = getRandom(1, 5);
                }
                System.out.println("count:" + amt);
                byte[][] bargainBytes = {
                        recordKey.getBytes(), limitKey.getBytes(), String.valueOf(amt).getBytes(), String.valueOf(firstType).getBytes(), String.valueOf(newMem ? 1 : 0).getBytes(), String.valueOf(0).getBytes()
                };
                res = connection.eval(checkAndBargainFixed.getBytes(), ReturnType.MULTI, 2, bargainBytes);

                System.out.println(res == null ? "" : res);
                Long aLong = (Long) res.get(0);
                if (aLong == 1) {
                    res.stream().forEach(System.out::println);
//                    if(res.get(1)!=0||res.get(3)!=0||res.get(2)<=0){
                    if ((Long) res.get(4) != 0 || (Long) res.get(3) <= 0) {
                        System.out.println("=========================");
                    }
                }
                System.out.println("+++++++++++++++++++++++++++++++++");
            }
            System.out.println("--------------------------------------");
        }
    }

    private static int getRandom(int start, int end) {
        if (start > end) {
            throw new RuntimeException();
        }
        if (end == start) {
            return end * 100;
        }
        Random random = new Random();
        return random.nextInt((end - start) * 100) + start * 100;
    }

    private boolean isNewMem() {
        Random random = new Random();
        return random.nextInt() > 7 ? true : false;
    }
}
