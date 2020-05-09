package org.tmdrk.toturial.thread.estimate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

/**
 * @ClassName PoolSizeCalculator
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/7 14:34
 * @Version 1.0
 **/
public abstract class PoolSizeCalculator {

    /**
     * The sample queue size to calculate the size of a single {@link Runnable}
     * element.
     */
    private static final int SAMPLE_QUEUE_SIZE = 1000;

    /**
     * Accuracy of test run. It must finish within 20ms of the testTime
     * otherwise we retry the test. This could be configurable.
     */
    private static final int EPSYLON = 20;

    /**
     * Control variable for the CPU time investigation.
     */
    private volatile boolean expired;

    /**
     * Time (millis) of the test run in the CPU time calculation.
     */
    private final long elapsed = 3000;

    /**
     * Calculates the boundaries of a thread pool for a given {@link Runnable}.
     *
     * @param targetUtilization the desired utilization of the CPUs (0 <= targetUtilization <= 1)
     * @param targetQueueSizeBytes the desired maximum work queue size of the thread pool (bytes)
     */
    void calculateBoundaries(BigDecimal targetUtilization, BigDecimal targetQueueSizeBytes) {
        calculateOptimalCapacity(targetQueueSizeBytes);
        Runnable task = createTask();
        start(task);
        start(task); // warm up phase
        long duration = System.currentTimeMillis();
        long cputime = getCurrentThreadCPUTime();
        start(task); // test interval
        cputime = getCurrentThreadCPUTime() - cputime;
        duration = System.currentTimeMillis()-duration;
        System.out.println("duration:"+duration * 1000000);
        long waitTime = (elapsed * 1000000) - cputime;
        System.out.println("elapsed:"+elapsed * 1000000);
        calculateOptimalThreadCount(cputime, waitTime, targetUtilization);
    }

    private void calculateOptimalCapacity(BigDecimal targetQueueSizeBytes) {
        long mem = calculateMemoryUsage();
        BigDecimal queueCapacity = targetQueueSizeBytes.divide(new BigDecimal(mem),
                RoundingMode.HALF_UP);
        System.out.println("Target queue memory usage (bytes): "
                + targetQueueSizeBytes);
        System.out.println("createTask() produced " + createTask().getClass().getName() + " which took " + mem + " bytes in a queue");
        System.out.println("Formula: " + targetQueueSizeBytes + " / " + mem);
        System.out.println("* Recommended queue capacity (bytes): " + queueCapacity);
    }

    /**
     * Brian Goetz' optimal thread count formula, see 'Java Concurrency in
     * * Practice' (chapter 8.2) 	 *
     * * @param cpu
     * *            cpu time consumed by considered task
     * * @param wait
     * *            wait time of considered task
     * * @param targetUtilization
     * *            target utilization of the system
     */
    private void calculateOptimalThreadCount(long cpu, long wait,
                                             BigDecimal targetUtilization) {
        BigDecimal computeTime = new BigDecimal(cpu);
        BigDecimal waitTime = new BigDecimal(wait);
        BigDecimal numberOfCPU = new BigDecimal(Runtime.getRuntime()
                .availableProcessors());
        BigDecimal optimalthreadcount = numberOfCPU.multiply(targetUtilization)
                .multiply(new BigDecimal(1).add(waitTime.divide(computeTime,
                        RoundingMode.HALF_UP)));
        System.out.println("Number of CPU: " + numberOfCPU);
        System.out.println("Target utilization: " + targetUtilization);
        System.out.println("Elapsed time (nanos): " + (elapsed * 1000000));
        System.out.println("Compute time (nanos): " + cpu);
        System.out.println("Wait time (nanos): " + wait);
        System.out.println("Formula: " + numberOfCPU + " * "
                + targetUtilization + " * (1 + " + waitTime + " / "
                + computeTime + ")");
        System.out.println("* Optimal thread count: " + optimalthreadcount);
    }

    /**
     * * Runs the {@link Runnable} over a period defined in {@link #elapsed}.
     * * Based on Heinz Kabbutz' ideas
     * * (http://www.javaspecialists.eu/archive/Issue124.html).
     * *
     * * @param task
     * *            the runnable under investigation
     */
    public void start(Runnable task) {
        long start = 0;
        int runs = 0;
        do {
            System.out.println("=============== runs:"+runs+"================");
            if (++runs > 10) {
                throw new IllegalStateException("Test not accurate");
            }
            expired = false;
            start = System.currentTimeMillis();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    expired = true;
                }
            }, elapsed);
            while (!expired) {
                task.run();
            }
            start = System.currentTimeMillis() - start;
            timer.cancel();
        } while (Math.abs(start - elapsed) > EPSYLON);
        collectGarbage(3);
    }

    private void collectGarbage(int times) {
        for (int i = 0; i < times; i++) {
            System.gc();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Calculates the memory usage of a single element in a work queue. Based on
     * Heinz Kabbutz' ideas
     * (http://www.javaspecialists.eu/archive/Issue029.html).
     *
     * @return memory usage of a single {@link Runnable} element in the thread
     * pools work queue
     */
    private long calculateMemoryUsage() {
        BlockingQueue<Runnable> queue = createWorkQueue(SAMPLE_QUEUE_SIZE);
        for (int i = 0; i < SAMPLE_QUEUE_SIZE; i++) {
            queue.add(createTask());
        }
        long mem0 = Runtime.getRuntime().totalMemory() -
                Runtime.getRuntime().freeMemory();
        long mem1 = Runtime.getRuntime().totalMemory() -
                Runtime.getRuntime().freeMemory();
        queue = null;
        collectGarbage(15);
        mem0 = Runtime.getRuntime().totalMemory()
                - Runtime.getRuntime().freeMemory();
        queue = createWorkQueue(SAMPLE_QUEUE_SIZE);
        for (int i = 0; i < SAMPLE_QUEUE_SIZE; i++) {
            queue.add(createTask());
        }
        collectGarbage(15);
        mem1 = Runtime.getRuntime().totalMemory()
                - Runtime.getRuntime().freeMemory();
        return (mem1 - mem0) / SAMPLE_QUEUE_SIZE;
    }

    /**
     * Create your runnable task here.
     *
     * @return an instance of your runnable task under investigation
     */
    protected abstract Runnable createTask();

    /**
     * Return an instance of the queue used in the thread pool.
     *
     * @return queue instance
     */
    protected abstract BlockingQueue<Runnable> createWorkQueue(int capacity);

    /**
     * Calculate current cpu time. Various frameworks may be used here,
     * depending on the operating system in use. (e.g.
     * http://www.hyperic.com/products/sigar). The more accurate the CPU time
     * measurement, the more accurate the results for thread count boundaries.
     *
     * @return current cpu time of current thread
     */
    protected abstract long getCurrentThreadCPUTime();
}
