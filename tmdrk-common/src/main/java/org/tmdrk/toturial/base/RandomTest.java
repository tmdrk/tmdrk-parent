package org.tmdrk.toturial.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @ClassName RandomTest
 * @Description
 * ThreadLocalRandom类原理: https://blog.csdn.net/reachwang/article/details/91351779
 * @Author zhoujie
 * @Date 2020/3/25 15:55
 * @Version 1.0
 **/
public class RandomTest {
    static ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    public static void main(String[] args) {
        for (int i=0;i<10;i++){
            System.out.println(ThreadLocalRandom.current().nextInt());
            System.out.println("static:"+threadLocalRandom.nextInt());
        }
        ExecutorService ex = Executors.newCachedThreadPool();
        for (int i=0;i<10;i++){
            ex.execute(()-> System.out.println("executor static:"+threadLocalRandom.nextInt()));
        }
        for (int i=0;i<1000;i++){
            ex.execute(()-> {
                ThreadLocalRandom t = ThreadLocalRandom.current();
                System.out.println("executor:"+t.nextInt()+" t:"+t.toString());
            });
        }
        ex.shutdown();
    }
}
