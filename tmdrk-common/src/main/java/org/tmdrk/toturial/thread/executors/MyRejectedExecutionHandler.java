package org.tmdrk.toturial.thread.executors;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName MyRejectedExecutionHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/21 12:38
 * @Version 1.0
 **/
public class MyRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (r instanceof MyRunnable) {
            MyRunnable myRunnable = (MyRunnable) r;
            //直接打印
            System.out.println("Task is rejected "+myRunnable.getMessage());
        }else{
            throw new RejectedExecutionException("Task " + r.toString() +
                    " rejected from " +
                    executor.toString());
        }
    }
}
