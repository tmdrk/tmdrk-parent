package org.tmdrk.toturial.thread;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @ClassName ThreadDemo
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/16 13:02
 * @Version 1.0
 **/
public class ThreadDemo extends Thread{
    BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    @Override
    public void run() {
        System.out.println("start...");
        while (true){
            try {
                Thread.sleep(2000);
                Runnable runnable = queue.take();
                System.out.println(queue.size());
                runnable.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void put(Runnable runnable) throws InterruptedException {
        queue.put(runnable);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();
        while(true){
            Thread.sleep(1000);
            threadDemo.put(()->{
                System.out.println("hello jim:"+ LocalDateTime.now());
            });
            System.out.println("=========");
        }
    }
}
