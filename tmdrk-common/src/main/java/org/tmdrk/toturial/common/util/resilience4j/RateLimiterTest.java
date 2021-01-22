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
                // 冷却时间
                .timeoutDuration(Duration.ofMillis(2000))
                .build();
        RateLimiter limiter = RateLimiter.of("learning", config);

        CheckedRunnable runnable = RateLimiter.decorateCheckedRunnable(limiter, () -> {
            log.info("NOW: {}",new Date());
        });
        // 执行4次
        Try.run(runnable)
                .andThenTry(runnable)
                .andThenTry(runnable)
                .andThenTry(runnable)
                .andThenTry(runnable)
                .andThenTry(runnable)
                .andThenTry(runnable)
                .andThenTry(runnable)
                .andThenTry(runnable)
                .andThenTry(runnable)
                .onFailure(t -> System.out.println(t.getMessage()));
    }
}
