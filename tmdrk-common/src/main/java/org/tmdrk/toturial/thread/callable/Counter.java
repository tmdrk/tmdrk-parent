package org.tmdrk.toturial.thread.callable;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @ClassName Counter
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/3/24 10:43
 * @Version 1.0
 **/
public class Counter implements Callable<String> {
    private String name;
    private int count;

    public Counter(String name,int count) {
        this.name = name;
        this.count = count;
    }

    public String call() throws Exception {
        Thread.sleep(2000);
        String result=null;
        Random random = new Random();
        int nextInt = random.nextInt(20);
        while(count-->0){
            Thread.sleep(nextInt*10);
            result=name+"----count------"+count;
            if(count==50){
                System.out.println(Thread.currentThread().getName()+" count:"+count+" name:"+name);
            }
            if (nextInt==10){
                System.out.println("随机数异常"+Thread.currentThread().getName()+" count:"+count+" name:"+name);
                throw new RuntimeException("随机数异常");
            }
        }
        return result;
    }
}
