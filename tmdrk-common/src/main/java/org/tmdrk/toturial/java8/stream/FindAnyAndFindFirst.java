package org.tmdrk.toturial.java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName FindAnyAndFindFirst
 * @Description 对比FindAny和FindFirst
 * @Author zhoujie
 * @Date 2020/2/20 11:06
 * @Version 1.0
 **/
public class FindAnyAndFindFirst {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for(int i = 0;i<100;i++){
            test1();
        }
        long start2 = System.currentTimeMillis();
        for(int i = 0;i<100;i++){
            test2();
        }
        long start3 = System.currentTimeMillis();
        System.out.println("====================================================");
        System.out.println(start2-start);
        System.out.println(start3-start2);
    }
    public static void test(){
        List<Integer> list = Arrays.asList(1, 2, 3, 6, 7, 47, 56, 444, 4, 23);
        Optional<Integer> any = list.parallelStream().filter(x -> x > 45).findAny();
        if(any.isPresent()){
            Integer res = any.get();
            System.out.println(res);
        }
    }
    public static void test1(){
        List<Integer> list = Arrays.asList(1, 2, 3, 6, 7, 47, 56, 444, 4, 23);
        Optional<Integer> first = list.parallelStream().filter(x -> x > 45).findFirst();
        if(first.isPresent()){
            Integer res = first.get();
            System.out.println(res);
        }
    }

    public static void test2(){
        List<String> lst1 = Arrays.asList("Jhonny", "David", "Jack", "Duke", "Jill","Dany","Julia","Jenish","Divya");
        List<String> lst2 = Arrays.asList("Jhonny", "David", "Jack", "Duke", "Jill","Dany","Julia","Jenish","Divya");

        Optional<String> findFirst = lst1.parallelStream().filter(s -> s.startsWith("D")).findFirst();
        Optional<String> fidnAny = lst2.parallelStream().filter(s -> s.startsWith("J")).findAny();

        System.out.println(findFirst.get()); //总是打印出David
        System.out.println(fidnAny.get()); //会随机地打印出Jack/Jill/Julia
    }
}
