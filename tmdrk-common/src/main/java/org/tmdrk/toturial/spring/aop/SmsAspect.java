package org.tmdrk.toturial.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import java.util.Arrays;

/**
 * @ClassName SmsAspect
 * @Description 拦截所有发短信方法
 * @Author zhoujie
 * @Date 2020/4/21 15:42
 * @Version 1.0
 **/
@Aspect
@Order(2)//值越小，切面越优先执行
public class SmsAspect {
    @Pointcut("execution(public int org.tmdrk.toturial.spring.aop.*.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void smsStart(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName()+"sms运行Before...运行参数：{"+ Arrays.asList(joinPoint.getArgs())+"}");
    }

    @After("org.tmdrk.toturial.spring.aop.LogAspect.pointCut()")
    public void smsEnd(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName()+"sms结束After...");
    }

    @AfterReturning(value="pointCut()",returning = "result")
    public Object smsReturn(JoinPoint joinPoint,Object result){
        System.out.println(joinPoint.getSignature().getName()+"sms正常返回AfterReturning... 运行结果：{"+result+"}");
        return result;
    }

    @AfterThrowing(value="pointCut()",throwing = "exception")
    public void smsException(JoinPoint joinPoint,Exception exception){
        System.out.println(joinPoint.getSignature().getName()+"sms异常AfterThrowing... 异常原因：{"+exception+"}");
    }

    @Around(value="pointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println(joinPoint.getSignature().getName()+"运行logAround...");
        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        //得到其方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取方法参数类型数组
        Class[] paramTypeArray = methodSignature.getParameterTypes();

        System.out.println("logAround请求参数为:"+Arrays.asList(args));
        //动态修改其参数
        //注意，如果调用joinPoint.proceed()方法，则修改的参数值不会生效，必须调用joinPoint.proceed(Object[] args)
        Object result = joinPoint.proceed(args);
        System.out.println("logAround响应结果为:"+result);
        //如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }
}
