package org.tmdrk.toturial.arithmetic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 在2,5,4,8,1,9,7,6,3中选取不相邻的数，使得总和最大。
 */
public class DynamicTest {
    public static void main(String[] args) {
//        int[] arr = new int[]{2,2,5,4,8,9,1,7,6,3};
        double[] arr = new double[]{138.08,1702.52,1241.98,1447.93,1855.87,274.4,634.2,534.57,926.78,259.61,1178.28,450.8,1323.84,1201.3,1882.09,686.47,1675.66,1805.36,367.78,1496.69,753.28,1386.88,1681.35,190.53,707.47,619.35,1550.12,1137.34,580.89,1643.82,678.3,1322.87,786.14,1715.95,1373.34,609.16,1842.25,1202.24,3787.58,1851.16,2870.1,1535.93,1683.69,255.6,129.62,237.91,1081.68};
//        System.color.println("doWork结果："+doWork(arr));
//        System.color.println("optimal2结果："+optimal2(arr));

        double target = 8508.84;
        List<Integer> list = new ArrayList<>();
        System.out.println("该数组中总和为"+target+"的组合为");
        subset2(arr,arr.length-1,target,list);

//        int index = 0;
//        StringBuffer sb = new StringBuffer();
//        for(int i=0;i<10000;i++){
//            double rand = Math.random();
//            index++;
//            if(index==50){
//                break;
//            }
//            if(rand*2000>100){
//                BigDecimal bg = new BigDecimal(rand*2000);
//                double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                sb.append(f1);
//                sb.append(",");
//                System.color.println(f1);
//            }
//        }
//        System.color.println(sb);
//
//        List<String> personList = new ArrayList<String>(){{
//            add("");
//        }};
    }

    public static int doWork(int[] arr){
        int[] indexs = new int[arr.length];
        int result = optimal2(arr,arr.length-1,indexs);
        for (int ind:indexs) {
            System.out.print(ind);
        }
        return result;
    }

    public static int optimal(int[] arr,int index,int[] indexs){
        if(index==0){
            return arr[index];
        }
        if(index==1){
            return max(arr[0],arr[index]);
        }
        int opt1 = optimal(arr,index-2,indexs)+arr[index];
        int opt2 = optimal(arr,index-1,indexs);
        return max(opt1,opt2);
    }

    public static int max(int para1,int para2){
        if(para1>para2){
            return para1;
        }else{
            return para2;
        }
    }

    public static int optimal2(int[] arr,int index,int[] indexs){
        int[] preFirstIndex = new int[arr.length];
        int[] preSecondIndex = new int[arr.length];
        if(index==0){
            System.out.println("前"+1+"个元素最大值为："+arr[index]);
            System.out.println("元素下标：");
            return arr[index];
        }
        if(index==1){
            System.out.println("前"+2+"个元素最大值为："+max(arr[0],arr[index]));
            return max(arr[0],arr[index]);
        }
        int pre1 = max(arr[0],arr[1]);
        if(arr[0]>arr[1]){
            preFirstIndex[0]=1;
        }else{
            preFirstIndex[1]=1;
        }
        int pre2 = arr[0];
        preSecondIndex[0] = 1;
        int maxValue;
        for(int i=2;i<=index;i++){
            pre2 = pre2+arr[i];
            if(pre2>pre1){
                preSecondIndex[i]=1;
                maxValue = pre2;
            }else{
                maxValue = pre1;
            }
            pre2 = pre1;
            pre1 = maxValue;
            System.out.println("前"+(i+1)+"个元素最大值为："+maxValue);
        }
        return 0;
    }

    public static void printArray(int[] arr){
        for(int i=0;i<arr.length;i++){
            System.out.println();
            System.out.print(arr[i]+",");
            System.out.println();
        }
    }

    public static boolean subset(double[] arr,int index,double sub,List indexs){
        if(index==0){
            if (arr[0]==sub){
                indexs.add(index);
                printList(arr,indexs);
                indexs.remove(indexs.size()-1);
                return false;
            }else{
                return false;
            }
        }
        if(sub-arr[index]==0){
            indexs.add(index);
            printList(arr,indexs);
            indexs.remove(indexs.size()-1);
            return false;
        }
        if(sub-arr[index]<0){
            return subset(arr,index-1,sub,indexs);
        }
        indexs.add(index);
        boolean select = subset(arr,index-1,sub-arr[index],indexs);
//        if(select){
//            printList(arr,indexs);
//            indexs.remove(indexs.size()-1);
//            select = false;
//        }
        indexs.remove(indexs.size()-1);
        boolean unselect = subset(arr,index-1,sub,indexs);
//        if(unselect){
//            printList(arr,indexs);
//            indexs.remove(indexs.size()-1);
//            unselect = false;
//        }
        return select||unselect;
    }

    public static void subset2(double[] arr,int index,double sub,List indexs){
        if(index==0){
            if (arr[0]==sub){
                indexs.add(index);
                printList(arr,indexs);
                indexs.remove(indexs.size()-1);
                return;
            }else{
                return;
            }
        }
        if(sub-arr[index]==0){
            indexs.add(index);
            printList(arr,indexs);
            indexs.remove(indexs.size()-1);
            subset2(arr,index-1,sub,indexs);
        }else if(sub-arr[index]<0){
            subset(arr,index-1,sub,indexs);
        }else{
            indexs.add(index);
            subset2(arr,index-1,sub-arr[index],indexs);
            indexs.remove(indexs.size()-1);
            subset2(arr,index-1,sub,indexs);
        }
    }

    public static void printList(double[] arr,List<Integer> indexs){
        System.out.println();
        for (Integer ind:indexs) {
            System.out.print(arr[ind]+"+");
        }
    }
}
