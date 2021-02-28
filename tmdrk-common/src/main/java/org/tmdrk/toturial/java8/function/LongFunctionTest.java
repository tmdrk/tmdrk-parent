package org.tmdrk.toturial.java8.function;

import org.tmdrk.toturial.entity.People;
import org.tmdrk.toturial.io.nio.protobuf.MyDataInfo;

import java.util.function.LongFunction;

/**
 * LongFunctionTest
 *
 * @author Jie.Zhou
 * @date 2021/2/4 14:17
 */
public class LongFunctionTest {
    private Long id;
    private People people;
    public LongFunctionTest(Long id,People people){
        this.id = id;
        this.people = people;
    }

    public static void main(String[] args) {
        People people = LongFunctionTest.longFunctionTest(13L, LongFunctionTest::assignPeople);
        System.out.println(people);
        people = LongFunctionTest.longFunctionTest(14L,new People().setAge(13).setIdCard("34032342")::setId);
        System.out.println(people);

        LongFunctionTest test = new LongFunctionTest(18L,people);
        People people1 = test.longFunctionTest(test::assignId);
        System.out.println(people1);
    }

    public static People longFunctionTest(Long id,LongFunction<People> function){
        return function.apply(id);
    }
    public static People assignPeople(Long id){
        return new People().setId(id);
    }

    public People longFunctionTest(LongFunction<People> function){
        return function.apply(id);
    }

    public People assignId(Long id){
        return people.setId(id).setUsername("嘿嘿");
    }
}
