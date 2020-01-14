package com.tmdrk.springmvc.controller;

import com.tmdrk.springmvc.service.DeferredResultQueue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * @ClassName HelloAsyncController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/14 12:17
 * @Version 1.0
 **/
@Controller
public class HelloAsyncController {

    /**
     * @Author zhoujie
     * @Description
     * 异步拦截器
     * 1 原生API的AsyncListener
     * 2SpringMVC实现AsyncHandlerInterceptor
     * @Date 12:36 2020/1/14
     * @Param []
     * @return java.util.concurrent.Callable<java.lang.String>
     **/
    @ResponseBody
    @RequestMapping("/asyn01")
    public Callable<String> asyn01(){
        System.out.println(Thread.currentThread().getName()+" start...");
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                System.out.println(Thread.currentThread().getName()+" start...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" end...");
                return "Callable<String> asyn01";
            }
        };
        System.out.println(Thread.currentThread().getName()+" end...");
        return callable;
    }

    @ResponseBody
    @RequestMapping("/createOrder")
    public DeferredResult<Object> createOrder(){
        DeferredResult deferredResult = new DeferredResult(5000L,"order create fail");
        deferredResult.onCompletion(()->{
            Object result = deferredResult.getResult();
            if(result.toString().equals("order create fail")){
                DeferredResultQueue.get();
            }
            System.out.println(deferredResult+" onCompletion "+deferredResult.getResult());
        });
        System.out.println(deferredResult);
        DeferredResultQueue.save(deferredResult);
        return deferredResult;
    }

    @ResponseBody
    @RequestMapping("/create")
    public String create(){
        String uuid = UUID.randomUUID().toString();
        DeferredResult<Object> objectDeferredResult = DeferredResultQueue.get();
        objectDeferredResult.setResult(uuid);
        System.out.println(objectDeferredResult);
        return "success ==> "+uuid;
    }
}
