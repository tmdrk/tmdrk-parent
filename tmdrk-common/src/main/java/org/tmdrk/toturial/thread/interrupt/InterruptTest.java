package org.tmdrk.toturial.thread.interrupt;

/**
 * @ClassName InterruptTest
 * @Description 线程中断测试，interrupt方法可用于中断某个线程
 * thread.interrupt() 线程实例中断方法
 * thread.isInterrupted() 返回boolean，判断线程实例是否调用了interrupt中断方法
 * Thread.interrupted() 返回boolean，判断当前线程是否调用了interrupt中断方法，并清除中断标识
 * @Author zhoujie
 * @Date 2020/4/26 0:25
 * @Version 1.0
 **/
public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        CountThread thread = new CountThread();
        thread.start();
        Thread.sleep(1);
        thread.interrupt();
        System.out.println(thread.getState()+"是否已经中断1："+thread.isInterrupted());//此处不一定为true，因为线程有可能已经死亡
        System.out.println(thread.getState()+"是否已经中断2："+Thread.interrupted());
        for(int i=0;i<100;i++){
            System.out.println(Thread.currentThread()+" i="+i);
        }
        System.out.println(thread.getState()+"是否已经停止1："+thread.isInterrupted());
    }
}
