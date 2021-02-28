package org.tmdrk.toturial.java8.function;

import org.tmdrk.toturial.entity.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * FunctionTest
 *
 * @author Jie.Zhou
 * @date 2021/2/3 15:06
 */
public class FunctionTest {
    public static void main(String[] args) {
        Function<String,Integer> f1 = FunctionTest::parseIntIncr;
        Function<Integer,Integer> f2 = s->{
            System.out.println("翻倍");
            return s*2;
        };
        System.out.println(functionTest("12",Integer::parseInt));
        System.out.println(functionTest("12",FunctionTest::parseIntIncr));
        System.out.println(functionTest("12",s->{
            return parseIntIncr(s);
        }));
        System.out.println(functionTest("12",f1.andThen(f2)));

        System.out.println(predicateTest("Start", s->s.startsWith("s")));
        System.out.println(predicateTest("Start", s->s.toLowerCase().startsWith("s")));

        consumerTest(new User(1L,"ZhouJie","13909878767","vip"),
                FunctionTest::consumerUser);

        System.out.println(supplierTest(User::new));
        System.out.println(supplierTest(()->new User(1L,"ZhouJie","13909878767","vip")));
        System.out.println(supplierTest(FunctionTest::getDefaultUser));


        //使用Number类的操作，里面只有double和int类型
        List<Number> list = new ArrayList<>();
        list.add(3);
        list.add(4.0);
        System.out.println(FunctionTest.map(t->{
            if (t.getClass() == Double.class) {
                return t.doubleValue()*t.doubleValue();
            } else {
                return t.intValue() * t.intValue();
            }
        }, list));

        //https://blog.csdn.net/gao_grace/article/details/94583317?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.control
        // BinaryOperator
        BinaryOperator<Long> intOper = BinaryOperator.maxBy(Comparator.naturalOrder());
        BinaryOperator<Long> op1 = (a, b) -> (a * b);
        Long aLong = FunctionTest.numberOperTest(2L, 4L, intOper);
        System.out.println(aLong);
        Long aLong1 = FunctionTest.numberOperTest(2L, 4L, op1);
        System.out.println(aLong1);
    }

    public static Integer functionTest(String age,Function<String,Integer> function) {
        return function.apply(age);
    }

    public static Integer parseIntIncr(String age){
        return Integer.parseInt(age)+1;
    }

    public static boolean predicateTest(String age, Predicate<String> predicate) {
        return predicate.test(age);
    }

    public static void consumerTest(User age, Consumer<User> consumer) {
        consumer.accept(age);
    }
    public static User consumerUser(User user){
        System.out.println("消费开始 "+user.toString());
        user.setUserName(user.getUserName().toLowerCase());
        System.out.println("消费结束 "+user.toString());
        return user;
    }

    public static User supplierTest(Supplier<User> supplier) {
        return supplier.get();
    }
    public static User getDefaultUser(){
        return new User(1L,"1","1","1");
    }

    public static<T extends Number> List<T> map(UnaryOperator<T> operator, List<T> list) {
        return list.stream().map(t -> operator.apply(t)).collect(Collectors.toList());
    }

    public static Long numberOperTest(Long n1,Long n2,BinaryOperator<Long> operator) {
        return operator.apply(n1,n2);
    }
}
