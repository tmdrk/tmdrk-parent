package org.tmdrk.toturial.arithmetic.kellyformula;

import org.tmdrk.common.CpuCoreUtil;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName KellyFormula
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/1 10:56
 * @Version 1.0
 **/
public class KellyFormula {
    static Random random = new Random();
    static int availableProcessors = CpuCoreUtil.getAvailableProcessors();
    static int coefficient = 2;
    static int threadNum = availableProcessors*coefficient;
    static ExecutorService cachedThreadPool = Executors.newFixedThreadPool(threadNum);
    static CountDownLatch cdl = new CountDownLatch(threadNum);

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        doWork(1000);
        long time2 = System.currentTimeMillis();
        doWorkParallel(1000);
        long time3 = System.currentTimeMillis();
        System.out.println("doWork cost time :"+(time2-time1));
        System.out.println("doWorkParallel cost time :"+(time3-time2));
        System.out.println("doWork/doWorkParallel :"+(time2-time1)/(time3-time2));
        cachedThreadPool.shutdownNow();



        double sum = 0;
        int investNum = 100000;//投资次数
        int investTotalMoney = 1000;//投资总钱数
        int investTotalNum = 300;//投资总次数
        double zeroCount = 0;
        double equalCount = 0;
        for(int i=0;i<investNum;i++){
            double principal = startInvest(investTotalMoney,investTotalNum);
            if(principal==0){
                zeroCount++;
            }
            if(principal<investTotalMoney){
                equalCount++;
            }
            sum += principal;
            if(principal>50000){
                System.out.println("principal:"+principal+" sum:"+sum);
            }
        }
        System.out.println("输钱率equalCount/investNum:"+equalCount/investNum+" 输完率zeroCount/investNum:"+zeroCount/investNum+" 收益率"+(sum-investNum*investTotalMoney)/investNum);

    }
    public static void doWork(int totalNum){
        double total = totalNum;
        double hit = 0;
        for (int i=0;i<totalNum;i++){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(judgeResult(60)){
                hit++;
            }
        }
        System.out.println("本次命中总数为："+hit);
        System.out.println("本次随机概率为："+(hit/total));
    }

    public static boolean judgeResult(int percentage){
        int num = random.nextInt(100);
//        System.out.println(num);
        if(num<percentage){
            return true;
        }
        return false;
    }

    public static void doWorkParallel(int totalNum){
        HashMap<String, Double> store = new HashMap<>();
        System.out.println("availableProcessors:"+threadNum);
        for(int i=0;i<threadNum;i++){
            final int index = i;
            Runnable runnable = getRunnable(totalNum, store, index);
            cachedThreadPool.execute(runnable);
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Set<Map.Entry<String, Double>> entries = store.entrySet();
        Iterator<Map.Entry<String, Double>> iterator = entries.iterator();

        double hits = 0;
        while(iterator.hasNext()){
            Map.Entry<String, Double> entry = iterator.next();
            Double value = entry.getValue();
            hits += value;
        }
        System.out.println("本次命中总数为："+hits);
        System.out.println("本次随机概率为："+(hits/totalNum));
    }

    private static Runnable getRunnable(int totalNum, HashMap<String, Double> store, int index) {
        return () -> {
            int shardNum = totalNum/threadNum;
            double hit = 0;
            for (int i=index*shardNum;i<(index+1)*shardNum;i++){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(judgeResult(60)){
                    hit++;
                }
            }
            store.put("key"+index,hit);
            cdl.countDown();
        };
    }


    public static double startInvest(double totalMoney,int totalNum){
        for (int i=0;i<totalNum;i++){
            double investMoney =0;
            if(totalMoney<100){
                investMoney = totalMoney;
            }else{
                investMoney = totalMoney*kellyCoefficient(0.55,0.93);
//                investMoney = totalMoney*0.25;
            }

            if(judgeResult(55)){
                totalMoney = totalMoney + investMoney*0.93;
            }else{
                totalMoney = totalMoney - investMoney;
            }
        }
        return totalMoney;
    }

    // f = (pb-(1-p))/b  p为获胜概率 b为赔率 f为每次投入本金的比例
    public static double kellyCoefficient(double p,double b){
        return (p*b-(1-p))/b;
    }
}
