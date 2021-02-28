package org.tmdrk.toturial.common.util.resilience4j.vavr;

import io.vavr.Tuple;
import io.vavr.Tuple3;

/**
 * TupleTest
 * 元组，含有多个类型对象集合
 * @author Jie.Zhou
 * @date 2021/2/19 13:29
 */
public class TupleTest {
    public static void main(String[] args) {
        //创建一个元组
        Tuple3<String, Integer, String> java8 = Tuple.of("Java", 8, "test");
        System.out.println(java8._1);
        System.out.println(java8._2);
        System.out.println(java8._3);

        //映射元组组件
        Tuple3<String, Integer, String> that = java8.map(
                s -> s.substring(2) + "lid",
                i -> i / 8,
                s -> s.substring(2) + "op"
        );
        System.out.println(that);

        //使用一个映射器映射一个元组
        Tuple3<String, Integer, String> that1 = java8.map(
                (s, i, s2) -> Tuple.of(s.substring(2) + "vr", i / 8, s2.substring(2) + "op")
        );
        System.out.println(that1);

        String that2 = java8.apply(
                (s, i ,s2) -> s.substring(2) + "vr " + i / 8 + s2.substring(2) + "op"
        );
        System.out.println(that2);

        //提升

    }
}
