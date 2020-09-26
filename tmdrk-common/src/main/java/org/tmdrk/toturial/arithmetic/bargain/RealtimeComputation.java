package org.tmdrk.toturial.arithmetic.bargain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * @ClassName RealtimeComputation
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/9/9 21:19
 * @Version 1.0
 **/
public class RealtimeComputation {
    public static void main(String[] args) {
        fixedComputation();
        System.out.println("=================");
//        for(int i=0;i<4;i++){
//            randomComputation();
//        }
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
}
