package org.tmdrk.toturial.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName InterruptTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/4/25 16:11
 * @Version 1.0
 **/
public class InterruptTest {
    public static void main(String[] args) throws Exception {
        MyTest1 myTest1 = new MyTest1();
        MyInterrupt myInterrupt = new MyInterrupt(myTest1);
        myInterrupt.start();
        myInterrupt.interrupt();
        Thread.sleep(50);
        System.out.println(Thread.currentThread().getName()+" "+myInterrupt.isInterrupted());
        System.out.println(Thread.currentThread().getName()+" "+myInterrupt.isInterrupted());

//        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().getName()+" "+myInterrupt.interrupted());
        System.out.println(Thread.currentThread().getName()+" "+myInterrupt.interrupted());
    }
}

class MyInterrupt extends Thread{
    MyTest1 myTest1;
    public MyInterrupt(MyTest1 myTest1){
        this.myTest1 = myTest1;
    }
    @Override
    public void run() {
        myTest1.doWork1();
    }
}

class MyTest1{
    public void doWork1(){
        int in = 1;
        try {
            Thread thread = Thread.currentThread();
//            Thread.sleep(2000);
//            System.color.println(Thread.currentThread().getName()+"进来了11 "+in+thread.interrupted());
//            System.color.println(Thread.currentThread().getName()+"休眠结束11"+in);


            for (int i = 0; i < 1000; i++) {
                System.out.println("i="+(i+1));
                if(thread.isInterrupted()){
                    System.out.println("通过this.isInterrupted()检测到中断");
                    System.out.println("第一个interrupted()"+thread.interrupted());
                    System.out.println("第二个interrupted()"+thread.interrupted());
                    break;
                }
            }
            System.out.println("因为检测到中断，所以跳出循环，线程到这里结束，因为后面没有内容了");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        }
    }
}