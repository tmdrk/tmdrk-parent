package org.tmdrk.toturial.classFrame.javassist.dubbo.customize;

import java.lang.reflect.Method;

/**
 * RpcInvocation
 *
 * @author Jie.Zhou
 * @date 2021/2/26 10:47
 */
public class RpcInvocation implements Invocation{
    private String methodName;
    private transient Class<?>[] parameterTypes;
    private Object[] arguments;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public RpcInvocation(){

    }
    public RpcInvocation(Method method, Object[] args) {
        methodName = method.getName();
        parameterTypes = method.getParameterTypes();
        arguments = args;
    }
}
