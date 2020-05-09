package org.tmdrk.toturial.java8.lambd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MyWork
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/16 1:25
 * @Version 1.0
 **/
public class MyWork {
    Map<String,MyFactory> map = new ConcurrentHashMap<>();
    int work(String key, MyFactory myFactory) {
        map.put(key,myFactory);
        return 0;
    }
}
