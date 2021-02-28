package org.tmdrk.toturial.classFrame.javassist.dubbo.customize;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * MyInvocationHandler
 *
 * @author Jie.Zhou
 * @date 2021/2/25 14:45
 */
public class MyInvocationHandler implements InvocationHandler {
    /**
     * 这个就是我们要代理的真实对象
     */
    private Invoker invoker;

    public MyInvocationHandler(Invoker invoker){
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理方法开始...");
//        Object invoke = method.invoke(subject, args);
        RpcInvocation rpcInvocation = new RpcInvocation(method,args);
        Result invoke = invoker.invoke(rpcInvocation);
        System.out.println("代理方法结束");
        return invoke.recreate();
    }
}
