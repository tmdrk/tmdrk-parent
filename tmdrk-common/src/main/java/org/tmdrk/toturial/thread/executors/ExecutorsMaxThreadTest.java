package org.tmdrk.toturial.thread.executors;

import sun.rmi.runtime.Log;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ExecutorsMaxThreadTest
 * @Description
 * corePoolSize 核心线程池大小
 * maximumPoolSize 最大线程池大小，当有界队列满了，会新建线程从队列中取任务消费，再把新任务存入队列
 * keepAliveTime 活跃时间（当工作队列没有任务时，空闲线程超过活跃时间没有接收到任务，此线程会被回收，回收对所有线程都是一致的）
 * unit 时间单位
 * blockingQueue 有界队列[ArrayBlockingQueue]，无界队列[LinkedBlockingQueue]，优先队列[PriorityBlockingQueue]，同步队列[SynchronizedQueue 特殊的BlockingQueue，对其的操作必须是放和取交替完成。]
 *      有界队列可以与maximumPoolSize，rejectedExecutionHandler共同搭配使用
 * threadFactory 线程池工场，可以自定义
 * rejectedExecutionHandler ArrayBlockingQueue和maximumPoolSize都满了，则会采用拒绝策略
 *      AbortPolicy 默认策略，抛异常
 *      DiscardPolicy 直接丢掉这个任务，不会有异常
 *      DiscardOldestPolicy 丢弃最老的。也就是说如果队列满了，会将最早进入队列的任务删掉腾出空间，再尝试加入队列。
 *      CallerRunsPolicy 主线程会自己去执行该任务，不会等待线程池中的线程去执行
 *      自定义
 *
 * @Author zhoujie
 * @Date 2020/1/21 11:11
 * @Version 1.0
 **/
public class ExecutorsMaxThreadTest {
    static AtomicInteger atomicInteger = new AtomicInteger();
//    public static void main(String[] args) throws InterruptedException {
//        System.out.println("the start!");
////        ExecutorService executorService = new ThreadPoolExecutor(5,10,5, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
//        ExecutorService executorService = new ThreadPoolExecutor(5,10,5, TimeUnit.SECONDS,new ArrayBlockingQueue(10),new MyThreadFactory("MyPool"), new MyRejectedExecutionHandler());
//        for (int i=1;i<=30;i++){
//            if(i%20==0){
//                Thread.sleep(2000);
//            }
//          /*  executorService.execute(()->{
//                try {
//                    int ai = atomicInteger.getAndIncrement();
//                    System.out.println(Thread.currentThread()+" 开始:"+ai);
//                    Thread.sleep(2000);
//                    System.out.println(Thread.currentThread()+" 结束:"+ai);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });*/
//            executorService.execute(new MyRunnable("myId-"+i));
//        }
//        executorService.shutdown();
//        System.out.println("===================================================== the end! =====================================================");
//    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("the start!");
//        ExecutorService executorService = new ThreadPoolExecutor(5,10,5, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        ExecutorService executorService = new ThreadPoolExecutor(2,4,20, TimeUnit.SECONDS,new ArrayBlockingQueue(2),new MyThreadFactory("MyPool"), new MyRejectedExecutionHandler());
        for (int i=1;i<=6;i++){
            executorService.execute(new MyRunnable("myId-"+i));
        }
        executorService.shutdown();
        System.out.println("===================================================== the end! =====================================================");
    }
}
