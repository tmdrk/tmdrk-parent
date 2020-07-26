package org.tmdrk.toturial.thread.interrupt;

/**
 * @ClassName CountThread
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/4/26 0:26
 * @Version 1.0
 **/
public class CountThread extends Thread{

    @Override
    public void run() {
        for(int i=0;i<1000;i++){
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(Thread.currentThread().isInterrupted());
            if(Thread.currentThread().isInterrupted()){
                System.out.println(Thread.currentThread()+" [is interrupt] "+" i="+i);
                break;
            }else{
                System.out.println(Thread.currentThread()+" i="+i);
            }
        }
        //isInterrupted方法，可以用来检查中断状态
        System.out.println(Thread.currentThread()+" isInterrupted:"+Thread.currentThread().isInterrupted()+" 执行中断后任务");
        //Thread.interrupted方法，可以用来检查并清除中断状态。
        System.out.println("Thread.interrupted():"+Thread.interrupted());
        System.out.println("Thread.interrupted():"+Thread.interrupted());
        System.out.println(Thread.currentThread()+" isInterrupted:"+Thread.currentThread().isInterrupted()+" 执行中断后任务");
        while(1==1){

        }
    }
}
