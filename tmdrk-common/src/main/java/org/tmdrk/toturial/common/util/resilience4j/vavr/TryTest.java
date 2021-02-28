package org.tmdrk.toturial.common.util.resilience4j.vavr;

import cn.hutool.core.lang.hash.Hash32;
import io.vavr.*;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.util.Random;
import java.util.function.Function;

import static com.google.common.base.Predicates.instanceOf;
import static io.vavr.API.*;
import static io.vavr.Patterns.*;
import static io.vavr.Predicates.isIn;

/**
 * TryTest
 * 参考网站：https://blog.csdn.net/ssehs/article/details/105831916
 * @author Jie.Zhou
 * @date 2021/2/19 11:20
 */
public class TryTest {
    public static void main(String[] args) {
        int div1 = divide(12, 1);
        System.out.println("div1="+div1);
        Try<Integer> div2 = tryDivide(12, 0);
        System.out.println("div2="+div2);
        System.out.println("isSuccess="+div2.isSuccess());
        System.out.println("isFailure="+div2.isFailure());
//        System.out.println("get="+div2.get());
        System.out.println("getOrElse="+div2.getOrElse(4));
        System.out.println("getCause="+div2.getCause());

        //提升
        Function2<Integer, Integer, Integer> divide = (a, b) -> a / b;
        //我们使用lift将divide转化为一个定义了所有输入的总函数。
        Function2<Integer, Integer, Option<Integer>> safeDivide = Function2.lift(divide);
        // = None
        Option<Integer> i1 = safeDivide.apply(1, 0);
        // = Some(2)
        Option<Integer> i2 = safeDivide.apply(4, 2);
        System.out.println("i1="+i1+" isEmpty="+i1.isEmpty());
        System.out.println("i2="+i2+" isEmpty="+i2.isEmpty()+" value="+i2.get());

        Function2<Integer, Integer, Option<Integer>> sum = Function2.lift(TryTest::sum);//this::sum
        // = None
        Option<Integer> optionalResult = sum.apply(-1, 2);
        System.out.println("optionalResult="+optionalResult);

        //部分应用
        //第一个参数a固定为值2。
        Function3<Integer, Integer, Integer, Integer> sum3 = (a, b, c) -> a + b + c;
        Function2<Integer, Integer, Integer> add3 = sum3.apply(2);
        System.out.println(add3.apply(4));
        System.out.println(add3.apply(4).apply(3));

        Function5<Integer, Integer, Integer, Integer, Integer, Integer> sum5 = (a, b, c, d, e) -> a + b + c + d + e;
        Function2<Integer, Integer, Integer> add6 = sum5.apply(2, 3, 1);
        System.out.println(add6.apply(4, 3));
        //柯里化
        //您可能会注意到，除了使用.curried()之外，此代码与部分应用中给出的2-参数数量示例相同。对于高阶函数，区别就变得很明显了。
        Function2<Integer, Integer, Integer> sum2 = (a, b) -> a + b;
        Function1<Integer, Integer> add2 = sum2.curried().apply(2);
        System.out.println(add2.apply(4));

        Function3<Integer, Integer, Integer, Integer> s3 = (a, b, c) -> a + b + c;
        final Function1<Integer, Function1<Integer, Integer>> a2 = s3.curried().apply(2);
        System.out.println(a2.apply(4).apply(3));

        //记忆化
        Function0<Double> hashCache =
                Function0.of(Math::random).memoized();
        double randomValue1 = hashCache.apply();
        double randomValue2 = hashCache.apply();
        System.out.println(randomValue1==randomValue2);

        //Lazy  Lazy是一种一元容器类型，它表示一个延迟计算值。与Supplier相比，Lazy是记忆的，也就是说它只评估一次，因此是引用透明的。
        Lazy<Double> lazy = Lazy.of(Math::random);
        lazy.isEvaluated(); // = false
        lazy.get();         // = 0.123 (random generated)
        lazy.isEvaluated(); // = true
        lazy.get();         // = 0.123 (memoized)
        //Either
        Either<String,Integer> value = compute().right().map(i -> i * 2).toEither();
        //如果compute()的结果是Right(1)，那么值就是Right(2)。
        //如果compute()的结果为Left(“error”)，则该值为Left(“error”)。
        System.out.println("value="+value.getOrElse(1)+" isLeft="+value.isLeft());

        //Validation

        //Java的基本匹配
        //$()-通配符模式
        //$(value)-相等模式
        //$(predicate)-条件模式
        String s = API.Match("12").of(
                Case($("1"), "one"),
                Case($("2"), "two"),
                Case($("12"), "three"),
                Case($(), "?") //通配符模式$()解救匹配，如果没有匹配的情况，则会抛出匹配错误。
        );
        System.out.println("s="+s);
        Option<String> s1 = API.Match(1).option(
                Case($(0), "zero")
        );
        System.out.println("s1="+s1);

        String arg = "-v";
        API.Match(arg).of(
                API.Case($(Predicates.isIn("-h", "--help")), o -> API.run(()->{
                    System.out.println("-h:help");
                })),
                Case($(isIn("-v", "--version")), o -> run(()->{
                    System.out.println("-v:3.1.4");
                })),
                Case($(), o -> run(() -> {
                    throw new IllegalArgumentException(arg);
                }))
        );

        Integer idx = 2;
        Number plusOne = API.Match(idx).of(
                Case($(instanceOf(Integer.class)), i -> i + 1),
                Case($(instanceOf(Double.class)), d -> d + 10),
                Case($(), o -> { throw new NumberFormatException(); })
        );
        System.out.println("plusOne="+plusOne);

        //预定义的模式
        Try<Integer> try1 = Try.of(() -> 4 / 3);
        Integer res = Match(try1).of(
                Case($Success($()), v -> v),
                Case($Failure($()), x -> 0)
        );
        System.out.println("res="+res);

        //用户自定义模式
        //守卫
//        API.Match(optional).of(
//                Case($Optional($(v -> v != null)), "defined"),
//                Case($Optional($(v -> v == null)), "empty")
//        );
        Option<Integer> i3 = i2;
        String of = Match(i3).of(
                Case($Some($()), "defined"),
                Case($None(), "empty")
        );
        System.out.println("of="+of);
    }

    public static int divide(int dividend, int divisor) {
        // throws if divisor is zero
        return dividend / divisor;
    }

    public static Try<Integer> tryDivide(Integer dividend, Integer divisor) {
        return Try.of(() -> dividend / divisor);
    }

    public static int sum(int first, int second) {
        if (first < 0 || second < 0) {
            throw new IllegalArgumentException("Only positive integers are allowed");
        }
        return first + second;
    }
    public static Either<String,Integer> compute(){
        int i = new Random().nextInt(100);
        if(i<50){
            return Either.left("error");
        }
        return Either.right(i);
    }
}
