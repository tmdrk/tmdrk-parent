package org.tmdrk.toturial.arithmetic.bargain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName BargainTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/9/7 21:06
 * @Version 1.0
 **/
public class BargainTest {
    public static void main(String[] args) {
        BigDecimal count = new BigDecimal(6);
        BigDecimal price = new BigDecimal(20);
        randomB(count,price);
        System.out.println("===============");
        bargainList(6,2000);
    }
    /**
     * 随机递减
     * @param count 总刀数
     * @param price 原价
     */
    private static void randomB(BigDecimal count, BigDecimal price){
        //已砍价格的集合
        List<BigDecimal> alreadyList = new ArrayList<>();
        //已砍的钱的总和
        BigDecimal alreadyCut = BigDecimal.ZERO;
        for(int i = 0;i<count.intValue();i++){
            //此次砍价的最低钱数（总价-已砍总价/总次数-已砍次数）（相当于是向上随机）（转换为单位分）
            Integer min = (price.subtract(alreadyCut)).divide(count.subtract(new BigDecimal(alreadyList.size())),2,BigDecimal.ROUND_UP).multiply(new BigDecimal(100)).intValue();
            //此次砍价的最高钱数（最低价格的2倍）
            //这个倍数越高，砍价的幅度跳动越大。建议设置到1-2.（不能超过2.因为有可到导致总刀数不准确）
            Integer max = min*2;
            //此次砍的价格（最低钱数到最高钱数的随机）
            BigDecimal cutPrice = new BigDecimal(min + new Random().nextInt(max-min)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_UP);
            System.out.println(cutPrice);
            //最后一刀保证价格准确
            if(i==count.intValue()-1){
                cutPrice = price.subtract(alreadyCut);
            }
            alreadyCut = alreadyCut.add(cutPrice);
            alreadyList.add(cutPrice);
        }
        System.out.println(alreadyCut);
        System.out.println(alreadyList.size());
        System.out.println("-----------------------");
        alreadyList.stream().forEach(System.out::println);
    }

    private static void bargainList(int count, int price){
        //已砍价格的集合
        List<Integer> alreadyList = new ArrayList<>();
        //已砍的钱的总和
        int alreadyCut = 0;
        for(int i = 0;i<count;i++){
            //此次砍价的最低钱数（总价-已砍总价/总次数-已砍次数）（相当于是向上随机）（转换为单位分）
            int mod = (price-alreadyCut)%(count-alreadyList.size());
            int temp = (price-alreadyCut)/(count-alreadyList.size())*100;
            Integer min = mod==0?temp:temp+1;
            //此次砍价的最高钱数（最低价格的2倍）
            //这个倍数越高，砍价的幅度跳动越大。建议设置到1-2.（不能超过2.因为有可到导致总刀数不准确）
            Integer max = min*2;
            //此次砍的价格（最低钱数到最高钱数的随机）
            int bigDecimal = min + new Random().nextInt(max - min);
            int cutPrice = bigDecimal%100==0?bigDecimal/100:bigDecimal/100+1;
            System.out.println(cutPrice);
            //最后一刀保证价格准确
            if(i==count-1){
                cutPrice = price/(alreadyCut);
            }
            alreadyCut = alreadyCut+cutPrice;
            alreadyList.add(cutPrice);
        }
        System.out.println(alreadyCut);
        System.out.println(alreadyList.size());
        int sum = alreadyList.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);
    }
}
