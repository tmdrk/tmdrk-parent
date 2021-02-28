package org.tmdrk.toturial.java8.function;

import io.vavr.Function5;
import org.tmdrk.toturial.entity.People;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * BiFunction
 *
 * @author Jie.Zhou
 * @date 2021/2/4 11:36
 */
public class BiFunctionTest {
    public static void main(String[] args) {
        biFinctionTest();

        biPredicateTest();

        biConsumerTest();

        mutlFunctionTest();
    }

    private static void mutlFunctionTest() {
        People people = BiFunctionTest.multFunctionTest(
                13L,
                "zhangli",
                "2343234",
                23,
                new Date(),
                BiFunctionTest::getMultPeople);
        System.out.println("函数返回:" + people);
    }

    private static void biConsumerTest() {
        BiFunctionTest.consumerPeople(
                12,
                new People().setId(1L).setAge(18).setIdCard("3402221989"),
                BiFunctionTest::doConsumer);
    }

    private static void biPredicateTest() {
        BiPredicate<Integer, People> bp1 = BiFunctionTest::checkAge;
        BiPredicate<Integer, People> bp2 = BiFunctionTest::checkIdCard;
        boolean boo = BiFunctionTest.checkPeople(
                20,
                new People().setId(1L).setAge(18).setIdCard("3402221989"),
                bp1.or(bp2));
        System.out.println("boolean=" + boo);
    }

    private static void biFinctionTest() {
        List<People> list = getPeoples();
        List<People> oldList = BiFunctionTest.getPersonByAge(12, list, BiFunctionTest::filter);
        List<People> youngList = BiFunctionTest.getPersonByAge(12, list, BiFunctionTest::reverseFilter);
        System.out.println(oldList);
        System.out.println(youngList);
    }

    private static List<People> getPeoples() {
        List<People> list = new ArrayList<>();
        list.add(new People().setId(1L).setAge(8).setUsername("John"));
        list.add(new People().setId(2L).setAge(12).setUsername("Alice"));
        list.add(new People().setId(3L).setAge(18).setUsername("George"));
        list.add(new People().setId(4L).setAge(33).setUsername("Steve"));
        return list;
    }

    // 更加灵活的方式 让调用者实现过滤的条件 是大于还是小于
    public static List<People> getPersonByAge(int age, List<People> personList, BiFunction<Integer, List<People>, List<People>> biFunction) {
        return biFunction.apply(age, personList);
    }

    public static List<People> filter(int age, List<People> personList) {
        List<People> list = personList.stream().filter(p -> p.getAge() >= age).collect(Collectors.toList());
        return list;
    }

    public static List<People> reverseFilter(int age, List<People> personList) {
        List<People> list = personList.stream().filter(p -> p.getAge() < age).collect(Collectors.toList());
        return list;
    }

    private static boolean checkPeople(int c1, People c2, BiPredicate<Integer, People> predicate) {
        return predicate.test(c1, c2);
    }

    private static boolean checkAge(int age, People people) {
        if (people != null && people.getAge() > age) {
            return true;
        }
        return false;
    }

    private static boolean checkIdCard(int age, People people) {
        if (people != null && people.getIdCard().startsWith("3403")) {
            return true;
        }
        return false;
    }

    private static void consumerPeople(int c1, People c2, BiConsumer<Integer, People> consumer) {
        consumer.accept(c1, c2);
    }

    private static void doConsumer(Integer age, People people) {
        System.out.println("消费开始 " + people);
        people.setAge(age);
        System.out.println("消费结束 " + people);
    }

    private static People multFunctionTest(Long id, String username, String idCard, Integer age, Date birthDay, Function5<Long, String, String, Integer, Date, People> function) {
        return function.apply(id, username, idCard, age, birthDay);
    }

    private static People getMultPeople(Long id, String username, String idCard, Integer age, Date birthDay) {
        System.out.println("业务逻辑完成");
        return new People().setId(id).setUsername(username).setIdCard(idCard).setAge(age).setBirthDay(birthDay);
    }
}
