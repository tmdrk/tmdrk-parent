package org.tmdrk.toturial.common.util.resilience4j;

import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.vavr.control.Try;
import org.tmdrk.toturial.entity.User;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * TimeLimiter
 *
 * @author Jie.Zhou
 * @date 2021/1/25 15:30
 */
public class TimeLimiterTest {
    public static void main(String[] args) {
        for(long i=0;i<10000000000L;i++){
            if(i%100000000==0){
                System.out.println("循环次数 i="+i+" date="+new Date());
            }
        }

        // 创建限时器配置，最大执行时间为1s，超时将取消Future
        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(1))
                .cancelRunningFuture(true)
                .build();

        // 从该配置创建限时器
        TimeLimiter timeLimiter = TimeLimiter.of(config);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 将待执行任务提交到线程池，获取Future Supplier
        Supplier<Future<User>> futureSupplier = () -> executorService.submit(new Worker("zhou"));

        // 使用限时器包装Future Supplier
        Callable restrictedCall = TimeLimiter
                .decorateFutureSupplier(timeLimiter, futureSupplier);
        // 若任务执行超时，onFailure会被触发
        Try.of(restrictedCall::call)
                .onFailure(throwable -> {
                    System.out.println("A timeout possibly occurred.");
                });
        executorService.shutdown();
    }

    static class Worker implements Callable<User>{
        private String name;

        public Worker(String name){
            this.name = name;
        }

        @Override
        public User call() throws Exception {
            System.out.println("入參 name="+name);
            Thread.sleep(2000);
            User user = new User();
            user.setUserName(name);
            System.out.println("执行结束 name="+name);
            return user;
        }
    }

}
