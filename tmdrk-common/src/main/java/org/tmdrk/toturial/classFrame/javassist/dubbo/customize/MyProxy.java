package org.tmdrk.toturial.classFrame.javassist.dubbo.customize;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * MyProxy
 *
 * @author Jie.Zhou
 * @date 2021/2/25 14:28
 */
public class  MyProxy implements MyInterface{
    public static java.lang.reflect.Method[] methods;
    private InvocationHandler handler;
    static {
        List<Method> meds = new ArrayList<>();
        Method[] dms = MyInterface.class.getDeclaredMethods();
        for (int i = 0; i < dms.length; i++) {
            if(!Modifier.isStatic(dms[i].getModifiers())){
                meds.add(dms[i]);
            }
        }
        methods = meds.toArray(new Method[0]);
    }

    public MyProxy(){
    }
    public MyProxy(InvocationHandler handler){
        this.handler = handler;
    }

    public void setMethods(java.lang.reflect.Method[] methods){
        this.methods = methods;
    }

    public static void main(String[] args) throws Throwable {
        MyProxy myProxy = new MyProxy(new MyInvocationHandler(new MyRpc()));
        String s = myProxy.hello("你好");
        System.out.println(s);
    }

    @Override
    public String hello(String msg) throws Throwable {
        Object[] var2 = new Object[]{msg};
        Object var3 = this.handler.invoke(this, methods[0], var2);
        return (String)var3;
    }

}
