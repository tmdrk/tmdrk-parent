package org.tmdrk.toturial.thread.estimate;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @ClassName SimplePoolSizeCaculator
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/7 14:43
 * @Version 1.0
 **/
public class SimplePoolSizeCaculator extends PoolSizeCalculator{
    @Override
    protected Runnable createTask() {
        return new AsyncIOTask();
    }

    @Override
    protected BlockingQueue<Runnable> createWorkQueue(int capacity) {
        return new LinkedBlockingQueue<Runnable>(capacity);
    }

    @Override
    protected long getCurrentThreadCPUTime() {
        //the total CPU time for the current thread in nanoseconds
        return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
    }

    public static void main(String[] args) {
        PoolSizeCalculator poolSizeCalculator = new SimplePoolSizeCaculator();
        //其中指定期望CPU利用率为1.0（即100%），任务队列总大小不超过100,000字节
        poolSizeCalculator.calculateBoundaries(new BigDecimal(1.0), new BigDecimal(100000));
    }
}
