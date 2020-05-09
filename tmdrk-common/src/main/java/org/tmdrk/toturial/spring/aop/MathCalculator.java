package org.tmdrk.toturial.spring.aop;

/**
 * @ClassName MathCalculator
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/14 19:23
 * @Version 1.0
 **/
public class MathCalculator {
    public int div(int a,int b){
        System.out.println("MathCalculator.div("+a+","+b+")");
        return a/b;
    }
    public void divoid(int a,int b){
        System.out.println("MathCalculator.divoid("+a+","+b+")");
    }
}
