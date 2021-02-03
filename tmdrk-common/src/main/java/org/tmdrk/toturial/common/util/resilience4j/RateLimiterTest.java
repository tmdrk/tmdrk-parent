package org.tmdrk.toturial.common.util.resilience4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Date;

/**
 * RateLimiterTest
 *
 * @author Jie.Zhou
 * @date 2021/1/22 15:00
 */
@Slf4j
public class RateLimiterTest {
    public static void main(String[] args) {
        RateLimiterConfig config = RateLimiterConfig.custom()
                // 阈值刷新的时间
                .limitRefreshPeriod(Duration.ofMillis(1000))
                // 限制频次
                .limitForPeriod(3)
                // 超时时间
                .timeoutDuration(Duration.ofMillis(600))
                .build();
        RateLimiter limiter = RateLimiter.of("learning", config);

//        CheckedRunnable runnable = RateLimiter.decorateCheckedRunnable(limiter, () -> {
//            log.info("NOW: {}",new Date());
//        });
//        // 执行4次
//        Try.run(runnable)
//                .andThenTry(runnable)
//                .andThenTry(runnable)
//                .andThenTry(runnable)
//                .andThenTry(runnable)
//                .andThenTry(runnable)
//                .andThenTry(runnable)
//                .andThenTry(runnable)
//                .andThenTry(runnable)
//                .andThenTry(runnable)
//                .onFailure(t -> System.out.println(t.getMessage()));

        for(int i=0;i<10;i++){
            int idx = i;
//            limiter.executeRunnable(()->doSomething(idx));
            try {
                String str = limiter.executeCallable(() -> doSomething(idx));
                System.out.println(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 在下一轮控制中，阈值变更为100
        limiter.changeLimitForPeriod(10);
    }

    public static String doSomething(int idx){
        try {
            Thread.sleep(100);
            System.out.println("执行成功 idx="+idx + " date="+new Date());
            return "idx-" + idx;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
