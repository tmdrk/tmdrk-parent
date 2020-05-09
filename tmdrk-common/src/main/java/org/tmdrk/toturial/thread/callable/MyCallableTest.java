package org.tmdrk.toturial.thread.callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName MyCallableTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/3/24 10:41
 * @Version 1.0
 **/
public class MyCallableTest {
    public static void main(String[] args) {
        ExecutorService service= Executors.newCachedThreadPool();
//        ExecutorService service= Executors.newFixedThreadPool(4);
        List<Future<String>> results=new ArrayList<Future<String>>();
        for(int i=0;i<20;i++){
            results.add(service.submit(new Counter("Thread_"+(i+1), 100)));
        }
        System.out.println("------------------------------------------------");
        int i=0;
        for (Future<String> future : results) {
            i++;
            System.out.println(future.isDone());
            try {
                System.out.println(Thread.currentThread()+"future:"+future.get()+" i="+i);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                service.shutdown();
            }

        }
    }
}
