package com.tmdrk.springmvc.service;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @ClassName DeferredResultQueue
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/14 12:45
 * @Version 1.0
 **/
public class DeferredResultQueue {
    private static ConcurrentLinkedQueue<DeferredResult<Object>> queue = new ConcurrentLinkedQueue<DeferredResult<Object>>();
    public static void save(DeferredResult<Object> deferredResult){
        queue.add(deferredResult);
    }

    public static DeferredResult<Object> get(){
        return queue.poll();
    }
}
