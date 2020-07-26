package org.tmdrk.toturial.thread.interrupt;

/**
 * @ClassName InterruptedExceptionTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/6/22 2:56
 * @Version 1.0
 **/
public class InterruptedExceptionTest {
    public static void main(String[] args) throws InterruptedException {
        MyThread thread = new MyThread();
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
        System.out.println(thread.getState()+"是否已经中断1："+thread.isInterrupted());//此处不一定为true，因为线程有可能已经死亡
        System.out.println(thread.getState()+"是否已经中断2："+Thread.interrupted());
        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread()+" i="+i);
        }
        System.out.println(thread+" state:"+thread.getState()+" 是否已经中断："+thread.isInterrupted());
    }
}
class MyThread extends Thread{
    @Override
    public void run() {
        int sun = 0;
//        for(int i=0;i<100000;i++){
//            sun+=i;
//        }
        System.out.println("第一个循环结束 sun:"+sun);
        for(int i=0;!Thread.currentThread().isInterrupted();i++){
            System.out.println("index:"+i);
            System.out.println(Thread.currentThread()+" isInterrupted:"+Thread.currentThread().isInterrupted());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                //捕获中断异常后，虚拟机会把该线程中断状态设置为false
                System.out.println(Thread.currentThread()+" isInterrupted:"+Thread.currentThread().isInterrupted()+" 捕获中断异常！");
                //这里设置中断，使程序可以中断退出。如果不设置，程序可能永远执行下去
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        System.out.println("线程结束");
    }
}