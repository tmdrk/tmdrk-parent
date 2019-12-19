package org.tmdrk.toturial.thread.test;

/**
 * @ClassName RunnableTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/5/1 12:16
 * @Version 1.0
 **/
public class RunnableTest {
    public static void main(String[] args) {
        ThreadGroup group1 = new ThreadGroup("group1");

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().toString()+" t1 is working");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        MyWorker worker = new MyWorker(t1);

        SecurityManager s = System.getSecurityManager();
        ThreadGroup group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        Thread t = new Thread(group1, worker,
                "myThread",
                0);
        t.start();
    }
}
class MyWorker implements Runnable{
    Thread old;
    public MyWorker(Thread old){
        this.old = old;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().toString()+" worker is working");
        old.run();
    }
}
