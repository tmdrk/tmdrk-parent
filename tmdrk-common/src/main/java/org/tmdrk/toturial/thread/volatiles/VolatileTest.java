package org.tmdrk.toturial.thread.volatiles;

/**
 * @ClassName VolatileTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/4/14 21:57
 * @Version 1.0
 **/
public class VolatileTest {
    static volatile int sum = 10;
//    static int sum = 10;
    public static void main(String[] args) {
        VolatileTest volatileTest = new VolatileTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sum++;
                System.out.println(Thread.currentThread()+" sum:"+sum);
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int summ = sum;
                try {
                    Thread.sleep(501);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread()+" summ:"+summ);
            }
        }).start();
    }
}
