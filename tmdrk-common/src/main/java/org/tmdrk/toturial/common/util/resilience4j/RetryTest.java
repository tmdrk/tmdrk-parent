package org.tmdrk.toturial.common.util.resilience4j;

import com.google.inject.internal.cglib.proxy.$Factory;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.API;
import io.vavr.CheckedFunction0;
import io.vavr.Predicates;
import io.vavr.control.Try;
import org.tmdrk.toturial.common.exception.BusinessException;
import org.tmdrk.toturial.common.exception.OtherBunsinessException;

import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;
import java.util.function.Supplier;

import static io.vavr.API.$;

/**
 * RetryTest
 *
 * @author Jie.Zhou
 * @date 2021/1/25 11:50
 */
public class RetryTest {
    public static void main(String[] args) {

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(5)
                .waitDuration(Duration.ofMillis(1000))
//                .retryOnException(throwable -> API.Match(throwable).of(
//                        API.Case($(Predicates.instanceOf(WebServiceException.class)), true),
//                        API.Case($(), false)))
                .retryExceptions(IOException.class, RuntimeException.class)
                .ignoreExceptions(BusinessException.class, OtherBunsinessException.class)
                .build();

        // 从重试配置创建重试器
        Retry retry = Retry.of("id", config);
        // 使用重试器包装函数调用
        CheckedFunction0<String> retryableSupplier = Retry.decorateCheckedSupplier(retry, () -> {throw new WebServiceException("BAM!");});
        // 调用将会自动重试
        Try<String> result = Try.of(retryableSupplier)
                .recover((throwable) -> "Hello world from recovery function");
        System.out.println("Hello world from recovery function".equals(result.get()));

        retry.executeRunnable(()->sayHelloWorld("hello"));

        retry.executeRunnable(()->sayHelloWorld("hi baby",true));
    }

    public static String sayHelloWorld(String str){
        return sayHelloWorld(str,false);
    }

    public static String sayHelloWorld(String str,boolean boo){
        System.out.println("业务开始");
        if(boo){
            int i = new Random().nextInt(100);
            if(i > 20){
                throw new RuntimeException("业务异常 "+i);
            }
        }
        str = str + new Random().nextInt(100);
        System.out.println("业务完成");
        return str;
    }
}
