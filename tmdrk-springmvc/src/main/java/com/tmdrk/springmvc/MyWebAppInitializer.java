package com.tmdrk.springmvc;

import com.tmdrk.springmvc.config.AppConfig;
import com.tmdrk.springmvc.config.RootConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @ClassName MyWebAppInitializer
 * @Description TODO
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
