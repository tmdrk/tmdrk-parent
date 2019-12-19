package org.tmdrk.toturial.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName AQSTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/4/25 11:18
 * @Version 1.0
 **/
public class AQSTest {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        MyTest myTest = new MyTest();
        Thread1 t1 = new Thread1(myTest);
        Thread2 t2 = new Thread2(myTest);
        Thread3 t3 = new Thread3(myTest);
        Thread4 t4 = new Thread4(myTest);
//      t1.start();
//      t2.start();t3.start();t4.start();
        System.out.println("---------------");
        executor.execute(t1);
        executor.execute(t2);
        executor.execute(t3);
//        Thread.sleep(8000);
        executor.shutdown();
        System.out.println("t1.isAlive:"+t1.isAlive()+" "+t1.getState()+" "+t2.getState()+" "+t3.getState());
//      while(t1.isAlive()){
        while(t1.getState() != Thread.State.TERMINATED){
            try {
                System.out.println("线程"+t1.getName()+"运行正常 "+t1.getState()+" "+t2.getState()+" "+t3.getState());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("线程"+t1.getName()+"结束运行 "+t1.getState()+" "+t2.getState()+" "+t3.getState());
    }


}
class Thread1 extends Thread{
    MyTest myTest;
    public Thread1(MyTest myTest){
        this.myTest = myTest;
    }
    @Override
    public void run() {
        myTest.doWork1();
    }
}
class Thread2 extends Thread{
    MyTest myTest;
    public Thread2(MyTest myTest){
        this.myTest = myTest;
    }
    @Override
    public void run() {
        myTest.doWork2();
    }
}

class Thread3 extends Thread{
    MyTest myTest;
    public Thread3(MyTest myTest){
        this.myTest = myTest;
    }
    @Override
    public void run() {
        myTest.doWork3();
    }
}

class Thread4 extends Thread{
    MyTest myTest;
    public Thread4(MyTest myTest){
        this.myTest = myTest;
    }
    @Override
    public void run() {
        myTest.doWork4();
    }
}


class MyTest{
    Lock lock = new ReentrantLock();
    public void doWork1(){
        try {
            int i = 1;
            int time = 60000;
            System.out.println(Thread.currentThread().getName()+"进来了doWork"+i+" 休眠"+time+"秒开始1");
            Thread.sleep(time);
            System.out.println(Thread.currentThread().getName()+" doWork"+i +" 尝试获取锁");
            lock.lock();
            System.out.println(Thread.currentThread().getName()+" doWork"+i+" 获取锁成功");

            System.out.println(Thread.currentThread().getName()+"进来了doWork"+i+" 休眠"+time+"秒开始");
            Thread.sleep(time);
            System.out.println(Thread.currentThread().getName()+"休眠结束doWork"+i);
            System.out.println(Thread.currentThread().getName()+"执行结束doWork"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
    public void doWork2(){
        int i = 2;
        try {
            int time = 70000;
            System.out.println(Thread.currentThread().getName()+"进来了doWork"+i+" 休眠"+time+"秒开始1");
            Thread.sleep(time);
            System.out.println(Thread.currentThread().getName()+"doWork"+i +" 尝试获取锁");
            lock.lock();
            System.out.println(Thread.currentThread().getName()+" doWork"+i+" 获取锁成功");
            System.out.println(Thread.currentThread().getName()+"进来了doWork"+i+" 休眠"+time+"秒开始");
            Thread.sleep(time);
            System.out.println(Thread.currentThread().getName()+"休眠结束doWork"+i);
            System.out.println(Thread.currentThread().getName()+"执行结束doWork"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    public void doWork3() {
        try {
            int i = 3;
            int time = 10000;
            System.out.println(Thread.currentThread().getName()+"进来了doWork"+i+" 休眠"+time+"秒开始");
            Thread.sleep(time);
            System.out.println(Thread.currentThread().getName()+"休眠结束doWork"+i +" 并尝试获取锁");
            lock.lock();
            System.out.println(Thread.currentThread().getName()+" doWork"+i+" 获取锁成功");
            System.out.println(Thread.currentThread().getName()+"执行结束doWork"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    public void doWork4() {
        try {
            int i = 4;
            int time = 20000;
            System.out.println(Thread.currentThread().getName()+"进来了doWork"+i+" 休眠"+time+"秒开始");
            Thread.sleep(time);
            System.out.println(Thread.currentThread().getName()+"休眠结束doWork"+i +" 并尝试获取锁");
            lock.lock();
            System.out.println(Thread.currentThread().getName()+" doWork"+i+" 获取锁成功");
            System.out.println(Thread.currentThread().getName()+"执行结束doWork"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}