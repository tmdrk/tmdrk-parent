package com.tmdrk.springmvc.test;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * @ClassName MyWebAppInitializer
 * @Description
 *
 * springMVC官网，用java配置注册和初始化dispatcherServlet的实例
 * 该实例完成两个主要操作
 *  1 完成spring容器初始化  省去web.xml引入applicationContext.xml配置
 *  2 向web容器注册dispatcherServlet对象  省去web.xml中servleat引入dispatcherServlet配置
 * public class MyWebApplicationInitializer implements WebApplicationInitializer {
 *
 *     @Override
 *     public void onStartup(ServletContext servletCxt) {
 *
 *         // Load Spring web application configuration
 *         AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
 *         ac.register(AppConfig.class);
 *         ac.refresh();
 *
 *         // Create and register the DispatcherServlet
 *         DispatcherServlet servlet = new DispatcherServlet(ac);
 *         ServletRegistration.Dynamic registration = servletCxt.addServlet("app", servlet);
 *         registration.setLoadOnStartup(1);//容器初始化时就加载该servlet对象，1优先级最高，0不加载
 *         registration.addMapping("/app/*");
 *     }
 * }
 *
 * META-INF/services/javax.servlet.ServletContainerInitializer
 * org.springframework.web.SpringServletContainerInitializer
 * SPI web容器启动会扫描javax.servlet.ServletContainerInitializer，拿到里面的文件全路径，加载SpringServletContainerInitializer
 * 并根据@HandlesTypes(WebApplicationInitializer.class)，传入Set<Class<?>> webAppInitializerClasses，调用onStartUp方法
 *
 * @Author zhoujie
 * @Date 2020/1/13 21:49
 * @Version 1.0
 **/
public class MyWebAppInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext servletCxt) {
        System.out.println("MyWebAppInitializer.onStartup...");
        // Load Spring web application configuration
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        ac.register(TestConfig.class);
//        ac.refresh(); //配置类TestConfig加@EnableWebMvc注解后，该行代码必须要删除，不然会报错，原因与父子容器有关，需要深入理解springmvc源码。
        // Create and register the  DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(ac);
        ServletRegistration.Dynamic registration = servletCxt.addServlet("app", servlet);
        registration.setLoadOnStartup(1);//容器初始化时就加载该servlet对象，1:优先级最高，0:不加载
        registration.addMapping("/app/*");
    }
}
