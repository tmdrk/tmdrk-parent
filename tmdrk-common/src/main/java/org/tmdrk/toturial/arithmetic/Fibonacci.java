package org.tmdrk.toturial.arithmetic;

public class Fibonacci {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        System.out.println("getValue返回值："+getValue(45));
        long endTime = System.currentTimeMillis();
        System.out.println("getValue耗时："+(endTime-startTime));

        long startTime1 = System.currentTimeMillis();
        System.out.println("dynamicValue返回值："+dynamicValue(45));
        long endTime1 = System.currentTimeMillis();
        System.out.println("dynamicValue耗时："+(endTime1-startTime1));
    }


    public static long getValue(int index){
        if(index<=2){
            return 1;
        }else{
            return getValue(index-1)+getValue(index-2);
        }
    }
    public static long dynamicValue(int index){
        if(index<=2){
            return 1;
        }
        long pre1 =1;
        long pre2 =1;
        long temp = 0;
        for(int i=3;i<=index;i++){
            temp = pre1+pre2;
            pre1 = pre2;
            pre2 = temp;
        }
        return temp;
    }
}
