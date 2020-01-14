package org.tmdrk.toturial.collection;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @ClassName ConcurrentSkipListMapTest
 * @Description 跳表测试
 * @Author zhoujie
 * @Date 2019/12/27 16:06
 * @Version 1.0
 **/
public class ConcurrentSkipListMapTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap concurrentSkipListMap = new ConcurrentSkipListMap();
        for (int i=0;i<100;i++){
            concurrentSkipListMap.put("key_"+i,i);
        }
        concurrentSkipListMap.forEach((key,value) -> System.out.println(key+"|"+value));

        ConcurrentSkipListSet concurrentSkipListSet = new ConcurrentSkipListSet();
        for (int i=0;i<100;i++){
            concurrentSkipListSet.add(i);
        }
        concurrentSkipListSet.forEach(value-> System.out.println(value));
    }
}
