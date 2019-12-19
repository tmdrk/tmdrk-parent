package org.tmdrk.toturial.thread.test;

/**
 * @ClassName WaitAndNotifyTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/27 14:53
 * @Version 1.0
 **/
public class WaitAndNotifyTest {
    public static void main(String[] args) throws InterruptedException {
        WaitAndNotify waitAndNotify = new WaitAndNotify();
        waitAndNotify.await();
        System.out.println("结束");
    }
}
