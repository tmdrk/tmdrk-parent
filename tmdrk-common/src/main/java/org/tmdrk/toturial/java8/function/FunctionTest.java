package org.tmdrk.toturial.java8.function;

import cn.hutool.core.lang.func.Func;
import org.tmdrk.toturial.entity.User;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
}
