package com.tmdrk.servlet3;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @ClassName HelloListener
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/9 13:21
 * @Version 1.0
 **/
public class HelloListener implements ServletContextListener {
    /**
     * @Author zhoujie
     * @Description 监听ServletContext初始化
     * @Date 13:23 2020/2/9
     * @Param [servletContextEvent]
     * @return void
     **/
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("HelloListener...contextInitialized...");
    }

    /**
     * @Author zhoujie
     * @Description 监听ServletContext销毁
     * @Date 13:23 2020/2/9
     * @Param [servletContextEvent]
     * @return void
     **/
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("HelloListener...contextDestroyed...");
    }
}
