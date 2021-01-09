package org.tmdrk.toturial.common.util.cache;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.util.ArrayList;

/**
 * MyCacheTest
 *
 * @author Jie.Zhou
 * @date 2020/12/23 10:13
 */
public class CaffeineTest {

    static private Cache<Long, BargainItemSpuQueryDTO> activityCache  = Caffeine.newBuilder().expireAfterWrite(Duration.ofSeconds(10)).maximumSize(100).build();

    public static void main(String[] args) {
        long page = 1L;
        long size = 20L;

        BargainItemSpuQueryDTO dto = activityCache.get(page, k -> getByRedis(k));
        System.out.println(JSON.toJSONString(dto));

        dto = activityCache.getIfPresent(page);
        if(dto == null){
            dto = getByRedis(page);
            activityCache.put(page,dto);
        }
        System.out.println(JSON.toJSONString(dto));

        dto = activityCache.getIfPresent(page);
        if(dto == null){
            dto = getByRedis(page);
            activityCache.put(page,dto);
        }
        System.out.println(JSON.toJSONString(dto));

        page = 2L;
        dto = activityCache.getIfPresent(page);
        if(dto == null){
            dto = getByRedis(page);
            activityCache.put(page,dto);
        }
        System.out.println(JSON.toJSONString(dto));

        size = 10L;
        System.out.println(1+" << "+4+"="+(1 << 4));
        for(int i=1;i<100;i++){
            System.out.println(i+" << "+size+"="+(i << size));
        }

        size = 20L;
        System.out.println(1+" << "+4+"="+(1 << 4));
        for(int i=1;i<100;i++){
            System.out.println(i+" << "+size+"="+(i << size));
        }

        System.out.println(System.currentTimeMillis());
        long openTime = DateUtil.parse("20201222", "yyyyMMdd").getTime();
        System.out.println(openTime);
        openTime = DateUtil.parse("202 2 1222", "yyyyMMdd").getTime();
        System.out.println(openTime);
        openTime = DateUtil.parse(" 20201222", "yyyyMMdd").getTime();
        System.out.println(openTime);

    }

    private static BargainItemSpuQueryDTO getByRedis(long page) {
        System.out.println("redis努力加载中...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<BargainsInfoDTO> objects = new ArrayList<>();
        objects.add(new BargainsInfoDTO().setBargainNo("10001"+page));
        objects.add(new BargainsInfoDTO().setBargainNo("10002"+page));
        return new BargainItemSpuQueryDTO().setActivityId(12L).setList(objects).setPage(page);
    }

}
