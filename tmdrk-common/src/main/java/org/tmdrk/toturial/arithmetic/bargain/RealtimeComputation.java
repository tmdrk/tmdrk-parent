package org.tmdrk.toturial.arithmetic.bargain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * @ClassName RealtimeComputation
 * @Description 实时计算砍价金额
 * @Author zhoujie
 * @Date 2020/9/9 21:19
 * @Version 1.0
 **/
public class RealtimeComputation {
    public static void main(String[] args) {
        fixedComputation();
        System.out.println("=================");
        for(int i=0;i<1;i++){
            randomComputation();
        }
        System.out.println("=================");
        for(int i=0;i<2;i++){
            randomComputationByStep();
        }
    }
    public static void fixedComputation(){
        int money = 199;
        int cnt = 19;
        int tmp = cnt;
        int total = 0;
        for(int i=0;i < tmp; i++){
            BigDecimal bargain = BigDecimal.valueOf(money).divide(BigDecimal.valueOf(cnt),0, RoundingMode.CEILING);
            System.out.println(bargain);
            money = money-bargain.intValue();
            cnt--;
            total+=bargain.intValue();
        }
        System.out.println("money="+money+" total="+total);
    }

    public static void randomComputation(){
        int totalMoney = 10000;
        int money = totalMoney;
        int cnt = 10;
        int tmp = cnt;
        int total = 0;
        Random random = new Random();
        for(int i=0;i < tmp-1; i++){
            double v = random.nextDouble();
//            while(v<0.1){
//                v = random.nextDouble();
//            }
            BigDecimal bargain = BigDecimal.valueOf(money).divide(BigDecimal.valueOf(cnt),0, RoundingMode.FLOOR);
            bargain = bargain.multiply(BigDecimal.valueOf(1+v)).setScale(0, RoundingMode.FLOOR);
//            System.out.println(bargain.intValue());
            money = money-bargain.intValue();
            cnt--;
            total+=bargain.intValue();
        }
        System.out.println(totalMoney-total);
        System.out.println("money="+money+" total="+total);
        if(total>=totalMoney){
            System.out.println("出事啦");
        }
    }

    public static void randomComputationByStep(){
        int totalMoney = 10000;
        int money = totalMoney;
        int cnt = 10;
        int tmp = cnt;
        int total = 0;
        int reduceCnt = 1;
        Random random = new Random();
        for(int i=0;i < tmp-1; i+=reduceCnt){
            reduceCnt = random.nextInt(8)+1;
            while(cnt < reduceCnt){
                reduceCnt = random.nextInt(8)+1;
            }
            if(i == tmp-2){
                reduceCnt = 1;
            }
            for(int j=0;j<reduceCnt;j++){
                double v = random.nextDouble();
                BigDecimal bargain = BigDecimal.valueOf(money).divide(BigDecimal.valueOf(cnt),0, RoundingMode.FLOOR);
                bargain = bargain.multiply(BigDecimal.valueOf(1+v)).setScale(0, RoundingMode.FLOOR);
                System.out.println("reduceCnt="+reduceCnt+" reduceAmt="+bargain.intValue());
                money = money-bargain.intValue();
                cnt--;
                total+=bargain.intValue();
            }
        }
        System.out.println(totalMoney-total);
        System.out.println(" money="+money+" total="+total);
        if(total>=totalMoney){
            System.out.println("出事啦");
        }
    }
}
