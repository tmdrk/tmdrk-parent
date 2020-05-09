package org.tmdrk.toturial.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

/**
 * @ClassName LogAspect
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/14 19:25
 * @Version 1.0
 **/

@Aspect
@Order(-10)//值越小，切面越优先执行
public class LogAspect {

    //抽取公共切入点表达式
    //1 本类引用
    //2 外部类引用 需要全路径
    @Pointcut("execution(public * org.tmdrk.toturial.spring.aop.MathCalculator.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void logStart2(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName()+"除法运行Before2...运行参数：{"+ Arrays.asList(joinPoint.getArgs())+"}");
    }

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName()+"除法运行Before...运行参数：{"+ Arrays.asList(joinPoint.getArgs())+"}");
    }

    @After("org.tmdrk.toturial.spring.aop.LogAspect.pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName()+"除法结束After...");
    }

    @After("org.tmdrk.toturial.spring.aop.LogAspect.pointCut()")
    public void logEnd2(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName()+"除法结束After2...");
    }

    @After("org.tmdrk.toturial.spring.aop.LogAspect.pointCut()")
    public void logEnd0(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName()+"除法结束After0...");
    }

    @AfterReturning(value="pointCut()",returning = "result")
    public Object logReturn(JoinPoint joinPoint,Object result){
        System.out.println(joinPoint.getSignature().getName()+"除法正常返回AfterReturning... 运行结果：{"+result+"}");
        return result;
    }

    @AfterThrowing(value="pointCut()",throwing = "exception")
    public void logException(JoinPoint joinPoint,Exception exception){
        System.out.println(joinPoint.getSignature().getName()+"除法异常AfterThrowing... 异常原因：{"+exception+"}");
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
