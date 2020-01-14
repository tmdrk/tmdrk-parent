package com.tmdrk.servlet3;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName HelloAsyncServlet
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/14 11:51
 * @Version 1.0
 **/
@WebServlet(value = "/helloasync",asyncSupported = true)
public class HelloAsyncServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(Thread.currentThread().getName()+" start...");
        //支持异步处理asyncSupported = true
        //开启异步模式
        AsyncContext startAsync = req.startAsync();
//        asyncContext.start(()->{
//            try {
//                System.out.println(Thread.currentThread().getName()+" start...");
//                sayHello();
//                asyncContext.complete();
//                AsyncContext asyncContext1 = req.getAsyncContext();
//                ServletResponse response = asyncContext1.getResponse();
//                response.getWriter().write("helloasync...");
////                System.out.println(Thread.currentThread().getName()+" end...");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
        startAsync.start(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName()+" start...");
                    sayHello();
                    startAsync.complete();
//                    AsyncContext asyncContext = req.getAsyncContext();
                    ServletResponse response = startAsync.getResponse();
                    response.getWriter().write("helloasync...");
                    System.out.println(Thread.currentThread().getName()+" end...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

//        try {
//            sayHello();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        resp.getWriter().write("hello...");
        System.out.println(Thread.currentThread().getName()+" end...");
    }
    public void sayHello() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+" processing...");
        Thread.sleep(3000);
    }
}
