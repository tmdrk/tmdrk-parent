package org.tmdrk.interview;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Number
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/11/1 1:10
 * @Version 1.0
 **/
public class Number {
    static List<List<Integer>> res = new ArrayList<>();
    static List<List<Integer>> res1 = new ArrayList<>();
    public static void main(String[] args) {
//        int[] arr = new int[]{1,2,3,4};
        int length = 20;
        int[] arr = new int[length];
        for(int i=1;i<=length;i++){
            arr[i-1]=i;
        }
        LinkedList<Integer> list = new LinkedList<>();

        long start1 = System.currentTimeMillis();
        doWork(list,arr,-1);
        long start2 = System.currentTimeMillis();
//        res.stream().forEach(li->{
//            System.out.println(li);
//        });
        System.out.println(res.size()+" duration1:"+(start2-start1));
        long start3 = System.currentTimeMillis();
        doWork1(list,arr,0);
        long start4 = System.currentTimeMillis();
//        res1.stream().forEach(li->{
//            System.out.println(li);
//        });
        System.out.println(res.size()+" duration2:"+(start4-start3));
    }

    public static void doWork(LinkedList<Integer> list,int[] arr,int i){
        i++;
        if(i >= arr.length){
            return;
        }
        list.add(arr[i]);
        res.add(new ArrayList<>(list));
        doWork(list,arr,i);
        list.removeLast();
        doWork(list,arr,i);
    }

    public static void doWork1(LinkedList<Integer> list,int[] arr,int idx){
        if(idx >= arr.length){
            return;
        }
        for(int i=idx;i<arr.length;i++){
            list.add(arr[i]);
            res1.add(new ArrayList<>(list));
            doWork1(list,arr,i+1);
            list.removeLast();
        }
    }

    public static void doWork(int[] arr){
        LinkedList<Integer> list = new LinkedList<>();
        for(int i=0;i<arr.length;i++){
            list.add(arr[i]);
            System.out.println(list);
            if(i==arr.length-1){
                list.removeLast();
                int size = list.size();
                i = size;
            }

        }
    }
}
