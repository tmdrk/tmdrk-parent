package com.tmdrk.servlet3;

import com.tmdrk.servlet3.service.HelloService;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
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
        set.forEach(clazz->{
            System.out.println(clazz.getName());
        });
    }
}
