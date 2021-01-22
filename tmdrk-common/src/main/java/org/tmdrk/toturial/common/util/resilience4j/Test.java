package org.tmdrk.toturial.common.util.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
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
                // 断路器保持打开的时间 在到达设置的时间之后 断路器会进入到half open状态
                .waitDurationInOpenState(Duration.ofMillis(8000))
                // 当断路器处于half open状态时(环形缓冲区大小)
                .ringBufferSizeInHalfOpenState(4)
                // 熔断器在关闭状态时环状缓冲区的大小
                .ringBufferSizeInClosedState(4)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        CircuitBreaker breaker = registry.circuitBreaker("learn");
        // 获取断路器的状态
        System.out.println(breaker.getState());
        String result;
        result = breaker.executeSupplier(() -> doSomethingWithArgs("world"));
        System.out.println(result);
        System.out.println(breaker.getState());
        breaker.onError(0, new RuntimeException());
        System.out.println(breaker.getState());
        result = breaker.executeSupplier(() -> doSomethingWithArgs("world"));
        System.out.println(result);
        System.out.println(breaker.getState());
        result = breaker.executeSupplier(() -> doSomethingWithArgs("world"));
        System.out.println(result);
        System.out.println(breaker.getState());
        System.out.println(breaker.getState());
        breaker.onError(0, new IllegalArgumentException());
        System.out.println(breaker.getState());
//        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(breaker, () -> doSomethingWithArgs("hello resilience4j"));
//        Try<String> map = Try.of(supplier)
//                .map(v -> v + " hello kitty");
//        System.out.println("<========================================>");
//
//        System.out.println("是否成功：" + map.isSuccess());
//        System.out.println("获取值：" + map.get());
//        System.out.println(breaker.getState());
        result = breaker.executeSupplier(() -> doSomethingWithArgs("world"));
        System.out.println(result);
        System.out.println(breaker.getState());
        result = breaker.executeSupplier(() -> doSomethingWithArgs("world"));
        System.out.println(result);
        System.out.println(breaker.getState());
        breaker.onError(0, new RuntimeException());
        System.out.println(breaker.getState());
        result = breaker.executeSupplier(() -> doSomethingWithArgs("world"));
        System.out.println(result);
        System.out.println(breaker.getState());
        result = breaker.executeSupplier(() -> doSomethingWithArgs("world"));
        System.out.println(result);
        System.out.println(breaker.getState());
        result = breaker.executeSupplier(() -> doSomethingWithArgs("world"));
        System.out.println(result);
        System.out.println(breaker.getState());
    }

    public static String doSomethingWithArgs(String word){
        System.out.println("工作计划："+word);
        return word+" 001";
    }
}
