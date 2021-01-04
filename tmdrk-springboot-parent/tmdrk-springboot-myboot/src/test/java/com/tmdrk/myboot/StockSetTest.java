package com.tmdrk.myboot;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.tmdrk.myboot.bargain.dto.BargainItem;
import com.tmdrk.myboot.redis.BusinessException;
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

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * StockSetTest
 *
 * @author Jie.Zhou
 * @date 2020/12/4 14:29
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockSetTest {
    @Value("${bargain.script.stockUpdateScript}")
    private String stockUpdateScript;
    @Value("${bargain.script.stockUpdateRollback}")
    private String stockUpdateRollback;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    IRedisIncrService redisIncrService;

    @Test
    public void initStockCache(){
        List<BargainItem> itemList = getSaveBargainItems();
        String stockKey = "test:bargain:item:" + 101;
        redisTemplate.delete(stockKey);
        // 砍价商品库存初始化
        HashMap<String, Long> keyArgs = new HashMap<>();
        for (BargainItem bargainItem : itemList) {
            keyArgs.put(bargainItem.getId().toString(), bargainItem.getTotalStore().longValue());
            keyArgs.put(bargainItem.getId().toString() + "-total", bargainItem.getTotalStore().longValue());
            keyArgs.put(bargainItem.getId().toString() + "-used", 0L);
            keyArgs.put(bargainItem.getId().toString() + "-lock", 0L);
        }
        Result<Map<String, Long>> result = redisIncrService.hincr(stockKey, keyArgs);
        if (!result.successful()) {
            throw new BusinessException(result.getCode(), result.getMsg());
        }

        lockOrUseStock();
    }

    private List<BargainItem> getSaveBargainItems() {
        List<BargainItem> itemList = new ArrayList<>();
        BargainItem item1 = new BargainItem().setId(1L).setTotalStore(10);
        BargainItem item2 = new BargainItem().setId(2L).setTotalStore(10);
        BargainItem item3 = new BargainItem().setId(3L).setTotalStore(10);
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        return itemList;
    }

    @Test
    public void lockOrUseStock(){
        BargainItem bargainItem = new BargainItem().setId(2L);
        String stockKey = "test:bargain:item:" + 101;
        long lock = 5;

        HashMap<String, Long> keyArgs = new HashMap<>();
        keyArgs.put(bargainItem.getId().toString(), -lock);
        keyArgs.put(bargainItem.getId().toString() + "-lock", lock);
        Result<Map<String, Long>> result = redisIncrService.hincr(stockKey, keyArgs);
        if (!result.successful()) {
            throw new BusinessException(result.getCode(), result.getMsg());
        }

        long used = 2;
        HashMap<String, Long> usedArgs = new HashMap<>();
        usedArgs.put(bargainItem.getId().toString() + "-lock", -used);
        usedArgs.put(bargainItem.getId().toString() + "-used", used);
        result = redisIncrService.hincr(stockKey, usedArgs);
        if (!result.successful()) {
            throw new BusinessException(result.getCode(), result.getMsg());
        }
    }


    @Test
    public void updateStockCache(){
        List<BargainItem> itemList = getBargainItems();
        // 活动奖品库存更新
        String stockKey = "test:bargain:item:" + 101;
        RedisConnection connection = redisConnectionFactory.getConnection();
        // 排除不限制奖品
        itemList = itemList.stream().filter(item -> item.getTotalStore() > 0).collect(Collectors.toList());
        int size = itemList.size();
        byte[][] keyArgs = new byte[2 + size * 2][];
        keyArgs[0] = stockKey.getBytes();
        keyArgs[1] = String.valueOf(size).getBytes();
        for (int i = 0; i < size; i++) {
            BargainItem item = itemList.get(i);
            keyArgs[2 + i] = item.getId().toString().getBytes();
            keyArgs[2 + size + i] = item.getTotalStore().toString().getBytes();
        }
        List<Long> eval = connection.eval(stockUpdateScript.getBytes(), ReturnType.fromJavaType(List.class), 1, keyArgs);
        assert eval != null;
        Long result = eval.get(0);
        if (result < 0) {
            throw new BusinessException(500, "商品库存更新出错，新设置数量应该大于已使用数量");
        }


        Random random = new Random();
        int ran = random.nextInt(3);
        if(ran > 0){
            log.info("更新后异常,回滚更新");
            byte[][] backArgs = new byte[2 + size * 2][];
            backArgs[0] = stockKey.getBytes();
            backArgs[1] = String.valueOf(size).getBytes();
            for (int i = 0; i < size; i++) {
                BargainItem item = itemList.get(i);
                backArgs[2 + i] = item.getId().toString().getBytes();
                backArgs[2 + size + i] = eval.get(i+1).toString().getBytes();
            }
            eval = connection.eval(stockUpdateRollback.getBytes(), ReturnType.fromJavaType(List.class), 1, backArgs);
            assert eval != null;
            Long result1 = eval.get(0);
            if (result1 < 0) {
                throw new BusinessException(500, "商品库存更新回滚出错");
            }
        }
        connection.close();
    }

    private List<BargainItem> getBargainItems() {
        List<BargainItem> itemList = new ArrayList<>();
        BargainItem item1 = new BargainItem().setId(1L).setTotalStore(50);
        BargainItem item2 = new BargainItem().setId(2L).setTotalStore(6);
        BargainItem item3 = new BargainItem().setId(3L).setTotalStore(20);
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        return itemList;
    }
}
