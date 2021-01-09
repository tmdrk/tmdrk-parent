package org.tmdrk.toturial.thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 业务线程池工厂
 *
 * @author Jie.Zhou
 * @date 2020/8/7 11:11
 */
@Slf4j
public class BusinessThreadPoolExecutorFactory {
    //可用cpu核数
    private static int availableProcessors = Runtime.getRuntime().availableProcessors();

    public static ThreadPoolExecutor build(int corePoolSize,
                                           int workQueueCount) {
        return build(corePoolSize, availableProcessors, 5000, TimeUnit.MILLISECONDS, workQueueCount,null);
    }

    public static ThreadPoolExecutor build(int corePoolSize,
                                           int workQueueCount,
                                           String threadName) {
        return build(corePoolSize, availableProcessors, 5000, TimeUnit.MILLISECONDS, workQueueCount, threadName);
    }

    public static ThreadPoolExecutor build(int corePoolSize,
                                           int maximumPoolSize,
                                           int workQueueCount,
                                           String threadName) {
        return build(corePoolSize, maximumPoolSize, 5000, TimeUnit.MILLISECONDS, workQueueCount, threadName);
    }

    public static ThreadPoolExecutor build(int corePoolSize,
                                           int maximumPoolSize,
                                           long keepAliveTime,
                                           TimeUnit unit,
                                           int workQueueCount,
                                           String threadName) {
        return build(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueueCount, threadName, new LogRejectedExecutionHandler());
    }

    public static ThreadPoolExecutor build(int corePoolSize,
                                           int maximumPoolSize,
                                           long keepAliveTime,
                                           TimeUnit unit,
                                           int workQueueCount,
                                           String threadName,
                                           RejectedExecutionHandler handler) {
        log.info("build executor corePoolSize:{} maximumPoolSize:{} keepAliveTime:{} unit:{} workQueueCount:{} threadName:{} rejectedHandler:{}"
                ,corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueueCount,threadName,handler.getClass());
        return new ThreadPoolExecutor(corePoolSize,  Math.max(maximumPoolSize,corePoolSize), keepAliveTime, unit, new ArrayBlockingQueue(workQueueCount), new BusinessThreadFactory(threadName), handler);
    }
}
