package org.tmdrk.toturial.common.util.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;

import java.time.Duration;

/**
 * Test
 *
 * @author Jie.Zhou
 * @date 2021/1/20 18:46
 */
public class Test {
    public static void main(String[] args) {
//        normalCircuitBreaker();
        exceptionCircuitBreaker();
    }

    /**
     * 正常断路器
     * @return: void
     */
    public static void normalCircuitBreaker(){
        // 获取一个CircuitBreakerRegistry实例 可调用ofDefaults获取实例(可自定义属性)
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                // 默认50 故障率阀值百分比 超过这个阀值 断路器就会打开
                .failureRateThreshold(50)
                // 断路器保持打开时间 在到达设置时间后 断路器会进入到half open状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                // 当断路器处于half open状态时 环形缓冲区的大小
//                .ringBufferSizeInOpenState(2)
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();

        // 2种CircuitBreaker调用方式
        CircuitBreakerRegistry registry1 = CircuitBreakerRegistry.of(config);
        CircuitBreaker breaker = registry1.circuitBreaker("learn");

        CircuitBreaker breaker1 = registry.circuitBreaker("learn1", config);

        //
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(breaker, () -> "hello resilience4j");
        Try<String> map = Try.of(supplier)
                .map(v -> v + " you are fish");
        System.out.println(map.isSuccess());
        System.out.println(map.get());

        System.out.println("<========================================>");

        CheckedFunction0<String> supplier1 = CircuitBreaker.decorateCheckedSupplier(breaker1, () -> "hello resilience4j");
        Try<String> map1 = Try.of(supplier1)
                .map(v -> v + " you are fish");
        System.err.println(map1.isSuccess());
        System.err.println(map1.get());
    }

    /**
     * 异常断路器
     * @return: void
     */
    public static void exceptionCircuitBreaker(){
        //该方式使用默认的全局配置CircuitBreakerConfig创建熔断器实例，你也可以选择使用定制化的配置，可选项有：
        //触发熔断的失败率阈值
        //熔断器从打开状态到半开状态的等待时间
        //熔断器在半开状态时环状缓冲区的大小
        //熔断器在关闭状态时环状缓冲区的大小
        //处理熔断器事件的定制监听器CircuitBreakerEventListener
        //评估异常是否被记录为失败事件的定制谓词函数Predicate
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                // 默认50 故障率阀值百分比 超过这个阀值 断路器就会打开
                .failureRateThreshold(50)
                // 熔断器在关闭状态时环状缓冲区的大小
                .ringBufferSizeInClosedState(4)
                // 断路器保持打开的时间 在到达设置的时间之后 断路器会进入到half open状态
                .waitDurationInOpenState(Duration.ofMillis(5000))
                // 当断路器处于half open状态时(环形缓冲区大小)
                .ringBufferSizeInHalfOpenState(5)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        CircuitBreaker breaker = registry.circuitBreaker("learn");
        // 获取断路器的状态
        System.out.println(breaker.getState());
        doSomething(breaker);
        doSomething(breaker,true);
        doSomething(breaker);
        doSomething(breaker);
        doSomething(breaker);
        doSomething(breaker);
        doSomething(breaker,true);
//        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(breaker, () -> doSomethingWithArgs("hello resilience4j"));
//        Try<String> map = Try.of(supplier)
//                .map(v -> v + " hello kitty");
//        System.out.println("<========================================>");
//
//        System.out.println("是否成功：" + map.isSuccess());
//        System.out.println("获取值：" + map.get());
//        System.out.println(breaker.getState());
        doSomething(breaker);
        doSomething(breaker);
        doSomething(breaker,true);
        doSomething(breaker);
        doSomething(breaker,true);
        doSomething(breaker);
        doSomething(breaker,true);
        doSomething(breaker);
        doSomething(breaker);
        doSomething(breaker,true);
        doSomething(breaker,true);
    }

    public static String doSomething(CircuitBreaker breaker){
        return doSomething(breaker,false);
    }

    public static String doSomething(CircuitBreaker breaker,boolean exception){
        String result = null;
        try{
            result = breaker.executeSupplier(() -> doSomethingWithArgs("world",exception));
        }catch (Exception e){
            if(e instanceof CircuitBreakerOpenException){
                System.out.println("方法已经熔断中 OPEN");
            }else{
                e.printStackTrace();
            }
        }
        System.out.println("result="+result+" status="+breaker.getState());
        return result;
    }

    public static String doSomethingWithArgs(String word,boolean exception){
        System.out.println("执行工作计划："+word + " " + exception);
        if(exception){
            throw new RuntimeException();
        }
        return word+" 001";
    }
}
