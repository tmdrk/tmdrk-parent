package org.tmdrk.toturial.java8.stream;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName Test
 * @Description
 *
 * https://medium.com/better-programming/functional-programming-with-java-streams-f930e0e4d184
 *
 * Java 8为我们提供了Stream API，这是功能块的延迟序列数据管道。 它不是作为数据结构实现的，也不是通过直接更改其元素来实现的。
 * 它只是一个简单的管道，提供了可进行操作的脚手架，使其真正成为智能管道。
 * The parts of a stream can be separated into three groups:
 * 1.Obtaining the stream (source)
 * 2.Doing the work (intermediate operations)
 * 3.Getting a result (terminal operation)
 *
 * Stream Characteristics
 * 1.Laziness
 * 2.(Mostly) stateless
 * 3.Optimizations included(包括优化)
 * 4.Nonreusable(不可重用)
 * 5.Less boilerplate(更少的样板，如无显示循环代码)
 * 6.Easy parallelization(轻松并行)
 * 7.Primitive handling(基元处理IntStream，LongStream)
 * @Author zhoujie
 * @Date 2020/2/21 15:42
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) {
//        filter();
//        System.out.println("================");
//        distinct();
//        System.out.println("================");
//        mapping();
//        System.out.println("================");
//        skipOrLimit();
//        System.out.println("================");
//        sorted();
//        System.out.println("================");
//        peek();
//        System.out.println("================");
//        collect();
//        System.out.println("================");
        reduce();
//        System.out.println("================");
//        calculation();
//        System.out.println("================");
//        match();
//        System.out.println("================");
//        consume();
    }
    /**************************************************************/
    /******************** Obtaining the stream ********************/
    /**************************************************************/

    /**************************************************************/
    /*********************** Doing the work ***********************/
    /**************************************************************/
    public static void filter(){
        List<Integer> numList = Arrays.asList(1,2,3,4,5);
        numList.stream().filter(a -> a > 2).map( i -> i*i).forEach(a-> System.out.println(a));
    }

    public static void distinct(){
        List<Integer> numList = Arrays.asList(1,2,2,3,4,4,5);
        numList.stream().distinct().forEach(a-> System.out.println(a));
    }

    public static void mapping(){
        List<Integer> numList = Arrays.asList(1,2,3,4,5);
        numList.stream().map( i -> i*i).forEach(a-> System.out.println(a));
        List<String> numList1 = Arrays.asList("1","2","3","4","5");
        numList1.stream().mapToInt(s -> Integer.parseInt(s)).forEach(i-> System.out.println(i*2));
        numList1.stream().mapToLong(s -> Integer.parseInt(s)).forEach(System.out::println);

        //对给定单词列表 ["Hello","World"],你想返回列表["H","e","l","o","W","r","d"]
        String[] words = new String[]{"Hello","World"};
        Arrays.stream(words)
                .map(word -> word.split(","))
                .flatMap(Arrays::stream)
                .distinct().forEach(System.out::println);
    }
    public static void skipOrLimit(){
        List<Integer> numbers = Arrays.asList(11, 14, 13, 12, 15, 16);
        numbers.stream().skip(2).forEach(System.out::println);
        System.out.println("======================");
        numbers.stream().limit(3).forEach(System.out::println);
        System.out.println("======================");
        //这两个操作可用于对流实现分页操作
        int offset = 5;
        int limit = 10;
        List<Integer> collect = Stream.iterate(0, i -> i + 1)
                .filter(i -> i % 2 == 0)
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    public static void sorted(){
        List<Integer> numList = Arrays.asList(8, 2, 56, 9, 5, 7,24,4);
        numList.stream().sorted().forEach(System.out::println);
        numList.stream().sorted(Comparator.comparing(Integer::intValue)).forEach(System.out::println);
        numList.stream().sorted(Comparator.comparing(Integer::intValue).reversed()).forEach(System.out::println);
    }

    public static void peek(){
        //设计初衷是用来debug使用的
        List<Integer> numList = Arrays.asList(8, 2, 56, 9, 5, 7,24,4);
        numList.stream().peek(System.out::println);
    }

    /**************************************************************/
    /********************** Getting a result **********************/
    /**************************************************************/

    /********************** Aggregate to new collection/array **********************/
    public static void collect(){
        String[] arr ={"aa","ccc","sss"};
        System.out.println(Arrays.stream(arr).collect(Collectors.toList()));
        System.out.println(Arrays.stream(arr).collect(Collectors.joining("->", "[", "]")));

        List<Person> list= Lists.newArrayList();
        list.add(new Person(1,34,"zhangsan"));
        list.add(new Person(2,55,"zhangsan"));
        list.add(new Person(3,34,"zhangsan"));
        list.add(new Person(4,55,"zhangsan"));
        list.add(new Person(5,66,"zhangsan"));
        // 这样我们就得到了一个 以年龄为key,以这个年龄的人数为value的map了
//        Map<Integer, List<Person>> personGroups = list.stream().
//                collect(Collectors.groupingBy(Person::getAge, Collectors.toList()));
        Map<Integer, Long> personGroups = list.stream().
                collect(Collectors.groupingBy(Person::getAge, Collectors.counting()));
        personGroups.forEach((a,b)-> System.out.println("key:"+a+" value:"+b));

        Integer[] integers = Stream.of(1, 2, 3, 4, 5).toArray(Integer[]::new);
        Arrays.stream(integers).forEach(System.out::println);
    }

    /********************** Reduce to a single value **********************/
    public static void reduce(){
        //reduce累加
//        List<Integer> numList = Arrays.asList(1,2,3,4,5);
//        int result = numList.stream().reduce((a,b) -> {
//            System.out.println("a=" + a + ",b=" + b);
//            return a + b;
//        } ).get();
//        System.out.println(result);
        //reduce 指定初始累加值
        List<Integer> numList = Arrays.asList(1,2,3);
        int result = numList.stream().filter(v->v>1).map(v->v+1).reduce(0,(a,b) ->  a + b  );
        System.out.println(result);

        //第三种签名的用法相较前两种稍显复杂，犹豫前两种实现有一个缺陷，它们的计算结果必须和stream中的元素类型相同，如上面的代码示例，
        //stream中的类型为int，那么计算结果也必须为int，这导致了灵活性的不足，甚至无法完成某些任务， 比入我们咬对一个一系列int值求和，
        //但是求和的结果用一个int类型已经放不下，必须升级为long类型，此实第三签名就能发挥价值了，它不将执行结果与stream中元素的类型绑死。
//        List<Integer> numList = Arrays.asList(Integer.MAX_VALUE,Integer.MAX_VALUE);
//        long result = numList.stream().reduce(0L,(a,b) ->  a + b, (a,b)-> 0L );
//        System.out.println(result);

        //将一个int类型的ArrayList转换成一个String类型的ArrayList
//        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5, 6);
//        ArrayList<String> result = numList.stream().reduce(new ArrayList<String>(), (a, b) -> {
//            a.add("element-" + Integer.toString(b));
//            return a;
//        }, (a, b) -> null);
//        System.out.println(result);
    }

    /********************** Calculations **********************/
    public static void calculation(){
        List<String> strs = Arrays.asList("d", "b", "a", "c", "a");
        Optional<String> min = strs.stream().min(Comparator.comparing(Function.identity()));
        Optional<String> max = strs.stream().max((o1, o2) -> o1.compareTo(o2));
        System.out.println(String.format("min:%s; max:%s", min.get(), max.get()));// min:a; max:d
        long count = strs.stream().filter(a -> !a.equals("b")).count();
        System.out.println("count:"+count);
    }

    /********************** Matching **********************/
    public static void match(){
        List<String> strs = Arrays.asList("a", "a", "a", "a", "b");
        boolean aa = strs.stream().anyMatch(str -> str.equals("a"));
        boolean bb = strs.stream().allMatch(str -> str.equals("a"));
        boolean cc = strs.stream().noneMatch(str -> str.equals("a"));
        long count = strs.stream().filter(str -> str.equals("a")).count();
        System.out.println(aa);// TRUE
        System.out.println(bb);// FALSE
        System.out.println(cc);// FALSE
        System.out.println(count);// 4
    }
    /********************** Finding **********************/
    public static void find(){

    }

    /********************** Consuming **********************/
    public static void consume(){
        Stream.of("AAA","BBB","CCC").forEach(s->System.out.println("Output:"+s));
        Stream.of("AAA","BBB","CCC").parallel().forEach(s->System.out.println("Output:"+s));
        Stream.of("AAA","BBB","CCC").parallel().forEachOrdered(s->System.out.println("Output:"+s));
    }
}
