package com.tmdrk.servlet3;

import com.tmdrk.servlet3.service.HelloService;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

/**
 * @ClassName MyServletContainerInitializer
 * @Description 应用启动时运行onStartup方法
 * @Author zhoujie
 * @Date 2020/1/13 20:23
 * @Version 1.0
 **/
//容器启动时会将@HandlesTypes指定的这个类型下面的子类型（实现类，子接口等）传递过来
//传入感兴趣的类型
@HandlesTypes(value = {HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("感兴趣的类型");
        set.forEach(clazz->
            System.out.println(clazz.getName())
        );

        ServletRegistration.Dynamic helloServlet = servletContext.addServlet("helloServlet", new HelloServlet());
        helloServlet.addMapping("/hello");

        //注册监听器
        servletContext.addListener(HelloListener.class);
        //注册过滤器
        FilterRegistration.Dynamic helloFilter = servletContext.addFilter("helloFilter", HelloFilter.class);
        //配置filter的映射信息
        helloFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");
    }
}
