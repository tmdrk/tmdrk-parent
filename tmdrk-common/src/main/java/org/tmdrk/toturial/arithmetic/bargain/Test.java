package org.tmdrk.toturial.arithmetic.bargain;

import java.util.Random;

/**
 * Test
 * 砍价前百分比的人员砍掉大比例金额测试
 * @author Jie.Zhou
 * @date 2020/9/30 15:46
 */
public class Test {
    public static void main(String[] args) {
        double amt = 100;
        int cnt = 10;
        for(int i=0;i<10;i++){
            amt = amt- amt*(0.6);
            cnt--;
            System.out.println(amt);
        }

        double aa = 0;
        for(int i=1;i<=50;i++){
            aa = Math.round(i*0.2);
            System.out.println(i+" "+aa);
        }

        Random rd = new Random();
        for(int i=0;i<10;i++){
            System.out.println((String.valueOf(-rd.nextInt(2)+1)));
        }
    }
}
