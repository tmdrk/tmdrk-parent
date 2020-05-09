package org.tmdrk.toturial.thread.management;

import java.lang.management.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ManagementTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/7 18:08
 * @Version 1.0
 **/
public class ManagementTest {
    static Byte[] b;

    {
        b = new Byte[1024 * 1024];
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<100;i++){
            b = new Byte[1024*1024*i];
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<200;i++){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<100;i++){
                        try {
                            Thread.sleep(60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            showRuntimeMXBeanInfo();
            showThreadMXBeanInfo();
            showGarbageCollectorMXBeanInfo();
            System.out.println("=============================================================");
            Thread.sleep(3000);
        }

    }

    public static void showGarbageCollectorMXBeanInfo(){
        List<GarbageCollectorMXBean> aGCMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean garbageCollectorMXBean:aGCMXBeans){
            long collectionCount = garbageCollectorMXBean.getCollectionCount();
            long collectionTime = garbageCollectorMXBean.getCollectionTime();
            String[] memoryPoolNames = garbageCollectorMXBean.getMemoryPoolNames();
            String name = garbageCollectorMXBean.getName();
            System.out.println("GarbageCollectorMXBean collectionCount:"+collectionCount);
            System.out.println("GarbageCollectorMXBean collectionTime:"+collectionTime);
            System.out.println("GarbageCollectorMXBean memoryPoolNames:"+memoryPoolNames);
            System.out.println("GarbageCollectorMXBean name:"+name);
            System.out.println("--------------------------------------------------------");
        }
    }

    public static void showRuntimeMXBeanInfo(){
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String [] nodes = runtimeMXBean.getName().split("@");//节点名称
        for(String node:nodes){
            System.out.println("RuntimeMXBean node:"+node);
        }
        System.out.println("--------------------------------------------------------");
    }

    public static void showThreadMXBeanInfo(){
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long cputime = threadMXBean.getCurrentThreadCpuTime()/1000000;//cpu时间
        long livethread = threadMXBean.getThreadCount();//活动线程数
        long topthread = threadMXBean.getPeakThreadCount();//峰值线程数
        long deamonthread = threadMXBean.getDaemonThreadCount();//守护线程数
        long totalthread = threadMXBean.getTotalStartedThreadCount();//创建线程总数
        long [] ids = threadMXBean.getAllThreadIds();//所有线程ID号

        //计算各类状态线程数量
        long newcount=0,runcount=0,blockedcount=0,waitingcount=0,twaitcount=0,terminatedcount=0;
        List<ThreadInfo> threads = new ArrayList<ThreadInfo>();
        for(int i=0;i<ids.length;i++){
            ThreadInfo tInfo =  threadMXBean.getThreadInfo(ids[i],Integer.MAX_VALUE);
            threads.add(tInfo);
            Thread.State state = tInfo.getThreadState();
            if(state.equals(Thread.State.NEW)){
                newcount++;
            }else if(state.equals(Thread.State.RUNNABLE)){
                runcount++;
            }else if(state.equals(Thread.State.BLOCKED)){
                blockedcount++;
            }else if(state.equals(Thread.State.WAITING)){
                waitingcount++;
            }else if(state.equals(Thread.State.TIMED_WAITING)){
                twaitcount++;
            }else if(state.equals(Thread.State.TERMINATED)){
                terminatedcount++;
            }
        }

        System.out.println("ThreadMXBean newcount:"+newcount);
        System.out.println("ThreadMXBean runcount:"+runcount);
        System.out.println("ThreadMXBean blockedcount:"+blockedcount);
        System.out.println("ThreadMXBean waitingcount:"+waitingcount);
        System.out.println("ThreadMXBean twaitcount:"+twaitcount);
        System.out.println("ThreadMXBean terminatedcount:"+terminatedcount);
        System.out.println("--------------------------------------------------------");
    }
}
