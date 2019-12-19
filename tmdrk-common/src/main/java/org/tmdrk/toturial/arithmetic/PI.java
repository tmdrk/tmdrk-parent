package org.tmdrk.toturial.arithmetic;

import java.nio.channels.FileChannel;

/**
 * @ClassName PI
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/16 21:54
 * @Version 1.0
 **/
public class PI {
    public static void main(String[] args) {
        cal1();
        cal2();
    }

    public static void cal1(){
        long a = 1;
        double sum = 0;
        for(int i = 0;i<100000000L;i++){
            sum += 1/Math.pow(a,2);
            a++;
//            System.out.println(sum+":"+a);
        }
        sum = sum * 6;
        sum = Math.sqrt(sum);
        System.out.println("sum:"+sum);
    }

    public static void cal2(){
        long a = 1;
        int b = -1;
        double sum = 0;
        for(int i = 0;i<100000000L;i++){
            sum += 1/(a*Math.pow(b,i));
            a = a + 2;
        }
        sum = sum * 4;
        System.out.println("sum:"+sum);
    }
}
