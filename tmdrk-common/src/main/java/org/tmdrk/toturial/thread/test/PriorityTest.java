package org.tmdrk.toturial.thread.test;

/**
 * @ClassName PriorityTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/5/1 14:10
 * @Version 1.0
 **/
public class PriorityTest {
    public static void main(String[] args){
        Thread t1 = new Thread(new T1());
        Thread t2 = new Thread(new T2());
        t1.setPriority(Thread.NORM_PRIORITY + 3);
        t1.start();
        t2.start();
    }
}
class T1 implements Runnable{
    @Override
    public void run() {
        for(int i=0;i<=10000;i++){
            System.out.println("T1 : " + i);
        }
    }
}

class T2 implements Runnable{
    @Override
    public void run() {
        for(int i=0;i<=10000;i++){
            System.out.println("---T2 : " + i);
        }
    }
}