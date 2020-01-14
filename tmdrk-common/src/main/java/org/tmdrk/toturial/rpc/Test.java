package org.tmdrk.toturial.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName Test
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/2 23:31
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) {
        UserService proxy = getProxy(UserService.class);
        System.out.println(proxy.helloWorld());
    }

    public static <T> T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("method:" + method.getName());
//                method.invoke(proxy, args);
                return "Hello world";
            }
        });
        return (T) o;
    }
}
