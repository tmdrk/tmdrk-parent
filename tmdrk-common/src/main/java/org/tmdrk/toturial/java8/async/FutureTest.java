package org.tmdrk.toturial.java8.async;

import org.tmdrk.toturial.thread.pool.BusinessThreadPoolExecutorFactory;

import java.util.concurrent.*;

/**
 * Future
 *
 * @author Jie.Zhou
 * @date 2021/2/7 13:33
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ThreadPoolExecutor executor = BusinessThreadPoolExecutorFactory.build(2, 100);
        testCancel(executor);
        testTimeout(executor);
        executor.shutdown();
    }

    private static void testTimeout(ThreadPoolExecutor executor) throws InterruptedException, ExecutionException {
        Future<String> future = executor.submit(() -> {
            System.out.println("任务执行开始");
            Thread.sleep(1000);
            System.out.println("任务执行完成");
            return "success";
        });
        String result = null;
        try {
            result = future.get(1000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("result="+result);
    }

    private static void testCancel(ThreadPoolExecutor executor) throws InterruptedException, ExecutionException {
        Future<String> future = executor.submit(() -> {
            System.out.println("任务执行开始");
            Thread.sleep(1000);
            System.out.println("任务执行完成");
            return "success";
        });
        Thread.sleep(1001);
        if(future.isDone()){
            String result = future.get();
            System.out.println("result="+result);
        }else{
            future.cancel(true);
            System.out.println("任务超时取消");
        }
    }
}
