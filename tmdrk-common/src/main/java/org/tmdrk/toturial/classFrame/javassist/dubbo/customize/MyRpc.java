package org.tmdrk.toturial.classFrame.javassist.dubbo.customize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * MyRpc
 *
 * @author Jie.Zhou
 * @date 2021/2/25 14:48
 */
public class MyRpc implements Invoker{
    @Override
    public Result invoke(Invocation invocation) {
        RpcInvocation rpcInvocation = (RpcInvocation) invocation;
        System.out.println("发送rpc请求");
        GenericResult result = new GenericResult();
        try{
            //远程调用
            String paras = Arrays.stream(rpcInvocation.getArguments()).map(Object::toString).collect(Collectors.joining(", ", "[", "]"));
            result.setValue("server returnTime="+ LocalDateTime.now() +" clientMethod:["+rpcInvocation.getMethodName()+"] clientArgs:"+paras);
        }catch (Exception e){
            result.setException(e);
        }
        return result;
    }
}
