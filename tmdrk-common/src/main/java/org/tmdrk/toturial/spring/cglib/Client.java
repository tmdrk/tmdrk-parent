package org.tmdrk.toturial.spring.cglib;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @ClassName Client
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/8 19:34
 * @Version 1.0
 **/
public class Client {
    public static void main(String[] args) {
        // 代理类class文件存入本地磁盘方便我们反编译查看源码
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "F:\\tmp\\log");
        // 通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        // 设置enhancer对象的父类
        enhancer.setSuperclass(HelloService.class);
        // 设置enhancer的回调对象
        enhancer.setCallback(new MyMethodInterceptor());
        // 创建代理对象
        HelloService proxy = (HelloService)enhancer.create();
        System.out.println(proxy.getClass());
        // 通过代理对象调用目标方法
        proxy.sayHello();
    }
}
