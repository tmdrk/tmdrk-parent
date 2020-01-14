package com.tmdrk.servlet3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName HelloServlet
 * @Description
 * @Author zhoujie
 * @Date 2020/1/13 20:00
 * @Version 1.0
 **/

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(Thread.currentThread().getName()+" start...");
        try {
            sayHello();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resp.getWriter().write("hello...");
        System.out.println(Thread.currentThread().getName()+" end...");
    }
    public void sayHello() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+" processing...");
        Thread.sleep(3000);
    }
}
