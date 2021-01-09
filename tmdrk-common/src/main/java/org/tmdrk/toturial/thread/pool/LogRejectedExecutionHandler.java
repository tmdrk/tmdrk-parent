package org.tmdrk.toturial.thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义打印日志拒绝策略
 *
 * @author Jie.Zhou
 * @date 2020/8/7 10:55
 */
@Slf4j
public class LogRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        //拒绝策略，打印日志
        log.info("This task " + r.toString() + " rejected from " + executor.toString());
    }
}
