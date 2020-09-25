package org.tmdrk.toturial.io.nio.netty.wheeltimer;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * WheelTimerTest
 *
 * @author Jie.Zhou
 * @date 2020/9/25 13:53
 */
@Slf4j
public class WheelTimerTest {
    public static void main(String[] args) {
        HashedWheelTimer timer = new HashedWheelTimer(24, //时间轮一圈的长度
                TimeUnit.SECONDS,
                12);//时间轮的度刻
        TimerTask task = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                log.info("任务执行开始");
                Thread.sleep(2000);
                log.info("任务执行结束");
            }
        };
        timer.newTimeout(task, 5, TimeUnit.SECONDS);
        log.info("WheelTimerTest End");
    }
}
