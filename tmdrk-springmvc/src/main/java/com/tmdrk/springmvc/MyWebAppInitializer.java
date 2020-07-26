package com.tmdrk.springmvc;

import com.tmdrk.springmvc.config.AppConfig;
import com.tmdrk.springmvc.config.RootConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @ClassName MyWebAppInitializer
 * @Description
 * AbstractAnnotationConfigDispatcherServletInitializer会触发servletContext.addServlet(servletName, dispatcherServlet);注册dispatcherServlet
 *
 * 继承结构
 * Object (java.java.lang)
 *      AbstractContextLoaderInitializer (org.springframework.web.context)
 *          AbstractDispatcherServletInitializer (org.springframework.web.servlet.support)
 *              AbstractAnnotationConfigDispatcherServletInitializer (org.springframework.web.servlet.support)
 *                  MyWebAppInitializer (com.tmdrk.springmvc)
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
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    //获取根容器的配置类（spring的配置文件）父容器
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }

    //获取web容器的配置类（springMvc的配置文件）子容器
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { AppConfig.class };
    }

    //获取DispatcherServlet的映射信息
    //  "/"除了*.jsp都拦截
    //  "/*" 拦截所有请求
    @Override
    protected String[] getServletMappings() {
        //拦截所有请求
        return new String[] { "/" };
    }
}
