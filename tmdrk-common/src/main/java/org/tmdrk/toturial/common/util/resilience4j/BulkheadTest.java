package org.tmdrk.toturial.common.util.resilience4j;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedFunction1;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;

/**
 * BulkheadTest
 * https://blog.csdn.net/garfielder007/article/details/106699071
 * Bulkhead意指船舶中的隔舱板，它将船体分割为多个船舱，在船部分受损时可避免沉船。框架中的Bulkhead通过信号量的方式隔离不同
 * 种类的调用，并进行流控，这样可以避免某类调用异常危及系统整体。（注：不同于Hystrix，该框架不提供基于线程池的隔离）
 *
 * @author Jie.Zhou
 * @date 2021/1/25 16:35
 */
public class BulkheadTest {
    public static void main(String[] args) {
        // 创建自定义的Bulkhead配置
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(150)
                .maxWaitTime(100)
                .build();
        // 创建Bulkhead注册中心
        BulkheadRegistry registry = BulkheadRegistry.of(config);
        // 从注册中心获取默认配置的Bulkhead
        Bulkhead bulkhead1 = registry.bulkhead("foo");
        // 从注册中心获取自定义配置的Bulkhead
        Bulkhead bulkhead2 = registry.bulkhead("bar", config);

//      // 你也可以不经过注册中心，直接创建Bulkhead：
//        Bulkhead bulkhead1 = Bulkhead.ofDefaults("foo");
//        Bulkhead bulkhead2 = Bulkhead.of(
//                "bar",
//                BulkheadConfig.custom()
//                        .maxConcurrentCalls(50)
//                        .build()
//        );

//        // 创建Bulkhead实例
//        Bulkhead bulkhead = Bulkhead.of("testName", config);
//        // 用Bulkhead包装函数调用
//        CheckedFunction0<String> decoratedSupplier = Bulkhead.decorateCheckedSupplier(bulkhead, () -> "This can be any method which returns: 'Hello");
//        // 链接其它函数
//        Try<String> result = Try.of(decoratedSupplier)
//                .map(value -> value + " world'");
//        System.out.println(result.isSuccess());
//        System.out.println("This can be any method which returns: 'Hello world'".equals(result.get()));
//        System.out.println(bulkhead.getMetrics().getAvailableConcurrentCalls());

//        // 两个Bulkhead
//        Bulkhead bulkhead = Bulkhead.of("test", config);
//        Bulkhead anotherBulkhead = Bulkhead.of("testAnother", config);
//        // 用两个Bulkhead分别包装Supplier 和 Function
//        CheckedFunction0<String> decoratedSupplier
//                = Bulkhead.decorateCheckedSupplier(bulkhead, () -> "Hello");
//        CheckedFunction1<String, String> decoratedFunction
//                = Bulkhead.decorateCheckedFunction(anotherBulkhead, (input) -> input + " world");
//        // 链接函数
//        Try<String> result = Try.of(decoratedSupplier)
//                .mapTry(decoratedFunction::apply);
//        System.out.println(result.isSuccess());
//        System.out.println("Hello world".equals(result.get()));
//        System.out.println(bulkhead.getMetrics().getAvailableConcurrentCalls());
//        System.out.println(anotherBulkhead.getMetrics().getAvailableConcurrentCalls());



        // 创建并行度为2的Bulkhead
        config = BulkheadConfig.custom().maxConcurrentCalls(2).build();
        Bulkhead bulkhead = Bulkhead.of("test", config);
        // 该方法会进入Bulkhead
        bulkhead.isCallPermitted();
        bulkhead.isCallPermitted();
        System.out.println(bulkhead.getMetrics().getAvailableConcurrentCalls());
        bulkhead.onComplete();
        bulkhead.onComplete();
        System.out.println(bulkhead.getMetrics().getAvailableConcurrentCalls());

        // 经过上面两次调用，Bulkhead已饱和
        CheckedRunnable checkedRunnable = Bulkhead.decorateCheckedRunnable(bulkhead, () -> {throw new RuntimeException("BAM!");});
        Try result = Try.run(checkedRunnable);

        System.out.println(result.isSuccess());
        System.out.println(result.failed().get() instanceof BulkheadFullException);
        System.out.println(bulkhead.getMetrics().getAvailableConcurrentCalls());
    }
}
