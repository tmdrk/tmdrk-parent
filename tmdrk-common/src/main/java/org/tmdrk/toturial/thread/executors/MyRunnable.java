package org.tmdrk.toturial.thread.executors;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName MyRunnable
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/21 13:40
 * @Version 1.0
 **/
public class MyRunnable implements Runnable{
    static AtomicInteger atomicInteger = new AtomicInteger();
    String message;

    public MyRunnable(String message){
        this.message = message;
    }

    @Override
    public void run() {
        try {
            int ai = atomicInteger.getAndIncrement();
            System.out.println("========"+Thread.currentThread()+" 开始:"+ai+" "+message);
            Thread.sleep(100);
            System.out.println(Thread.currentThread()+" 结束:"+ai+" "+message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
