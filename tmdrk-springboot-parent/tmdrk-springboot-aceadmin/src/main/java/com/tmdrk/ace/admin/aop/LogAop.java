package com.tmdrk.ace.admin.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tmdrk.ace.admin.util.IpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName LogAop
 * @Description 打印请求和响应日志
 * @Author zhoujie
 * @Date 2020/5/5 22:37
 * @Version 1.0
 **/
@Aspect
@Order(2)
@Component
public class LogAop {
    Logger logger = LoggerFactory.getLogger(getClass());

    //抽取公共切入点表达式
    //1 本类引用
    //2 外部类引用 需要全路径
    @Pointcut("execution(public * com.tmdrk.ace.admin.controller.*.*(..))")
    public void pointCut(){}

    @Around(value="pointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
        logger.info("LogAop.logAround...");
        StringBuilder builder = new StringBuilder();
        //获取目标对象
        Object target = joinPoint.getTarget();
        //获取目标方法
        String methodName = joinPoint.getSignature().getName();
        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(a-> System.out.println(a.getClass()));
        List<Object> argsList= Arrays.stream(args).filter(a -> !((a instanceof HttpServletRequest) ||
                (a instanceof HttpServletResponse))).collect(Collectors.toList());
        //得到其方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取方法参数类型数组
        Class[] paramTypeArray = methodSignature.getParameterTypes();

        //打印请求信息
        logBeforeProceed(builder, target, methodName, argsList, paramTypeArray);

        long start = System.currentTimeMillis();
        //注意，如果调用joinPoint.proceed()方法，则修改的参数值不会生效，必须调用joinPoint.proceed(Object[] args)
        Object result = joinPoint.proceed(args);
        long duration = System.currentTimeMillis() - start;

        //打印响应信息
        logAfterProceed(builder, target, methodName, paramTypeArray, result, duration);

        //如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }

    private void logAfterProceed(StringBuilder builder, Object target, String methodName, Class[] paramTypeArray, Object result, long duration) {
        builder.delete(0,builder.length());
        builder.append("; duration:").append(duration).append("ms");
        builder.append("; target:").append(target);
        builder.append("; methodName:").append(methodName).append(Arrays.asList(paramTypeArray));
        builder.append("; return:\n").append(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
        logger.info("<<===== {}", builder.toString());
    }

    private void logBeforeProceed(StringBuilder builder, Object target, String methodName, List<Object> argsList, Class[] paramTypeArray) throws UnknownHostException {
        //获取renquest对象
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String contentType = request.getContentType();
        boolean multipart_form_data = false;
        if (contentType != null){
            //文件表单提交
            if (contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)){
                multipart_form_data = true;
            }
        }

        String requestedSessionId = request.getRequestedSessionId();
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();//url后面的请求参数
        String sourceIpAddr = IpUtil.getIpAddr(request);
        String method = request.getMethod();
        String hostIpAddr = InetAddress.getLocalHost().getHostAddress();

        builder.append(method).append(" ").append(requestURL);
        if(queryString!=null){
            builder.append("?").append(queryString);
        }
        builder.append("; sourceIpAddr:").append(sourceIpAddr);
        builder.append("; hostIpAddr:").append(hostIpAddr);
        builder.append("; requestedSessionId:").append(requestedSessionId);
        builder.append("; contentType:").append(contentType);
        builder.append("; target:").append(target);
        builder.append("; methodName:").append(methodName).append(Arrays.asList(paramTypeArray));
        if(!multipart_form_data){
            builder.append("; methodArgs:\n").append(JSON.toJSONString(argsList, SerializerFeature.WriteMapNullValue));
        }
        logger.info("=====>> {}",builder.toString());
    }

    @AfterReturning(value="pointCut()",returning = "result")
    public Object logReturn(JoinPoint joinPoint,Object result){
        return result;
    }

    @AfterThrowing(value="pointCut()",throwing = "exception")
    public void logException(JoinPoint joinPoint,Exception exception){
    }
}
