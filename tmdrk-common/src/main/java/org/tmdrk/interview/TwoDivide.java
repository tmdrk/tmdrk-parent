package org.tmdrk.interview;

/**
 * TwoDivide
 * 两数相除
 * @author Jie.Zhou
 * @date 2020/11/2 16:50
 */
public class TwoDivide {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int divide = divide(7,-3);
        long duration = System.currentTimeMillis()-start;
        System.out.println("duration:"+duration+" divide:"+divide);
    }

    public static int divide(int dividend, int divisor) {
        int baseDividend = dividend;
        if(dividend == 0){
            return 0;
        }
        boolean flag = true;
        if(dividend>0 && divisor<0){
            flag = false;
        }
        if(dividend>0 && divisor<0 || dividend<0 && divisor>0){
            flag = false;
        }
        if(dividend<0){
            if(dividend==Integer.MIN_VALUE && divisor<0){
                dividend = dividend+1;
            }
            dividend = -dividend;
        }
        if(divisor<0){
            if(divisor==Integer.MIN_VALUE){
                if(baseDividend != Integer.MIN_VALUE){
                    return 0;
                }
                divisor = divisor+1;
            }
            divisor = -divisor;
        }
        int baseDivisor = divisor;
        int idx = 1;
        int res = 0;
        while((dividend=dividend-divisor)>=0){
            res=res+idx;
            while(divisor < Integer.MAX_VALUE - divisor&& dividend-divisor-divisor>0){
                divisor = divisor+divisor;
                dividend = dividend - divisor;
                idx=idx+idx;
                res=res+idx;
            }
            idx = 1;
            divisor = baseDivisor;
        }
        return flag?res:-res;
    }
}
