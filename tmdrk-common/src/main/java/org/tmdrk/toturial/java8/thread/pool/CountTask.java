package org.tmdrk.toturial.java8.thread.pool;

import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer> {
    private int start;

    private int end;

    private int threshold;

    public CountTask(int start, int end, int threshold) {
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }

    @Override
    protected Integer compute() {
        try {
            //模拟延时
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println(Thread.currentThread().getName());
        if (end - start <= threshold) {
            int sum = 0;
            for (int i = start; i < end; ++i){
                sum += i;
            }
            System.out.println(Thread.currentThread().getName()+" sum="+sum);
            return sum;
        } else {
            int median = (start + end) / 2;
            CountTask left = new CountTask(start, median, threshold);
            CountTask right = new CountTask(median, end, threshold);
            left.fork();
            right.fork();
            int leftValue = left.join();
            int rightValue = right.join();
            return leftValue + rightValue;
        }
    }
}