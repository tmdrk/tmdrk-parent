package org.tmdrk.toturial.spring.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.tmdrk.toturial.spring.app.App1Config;
import org.tmdrk.toturial.spring.app.MainConfigOfPropertyValues;
import org.tmdrk.toturial.spring.service.redis.CodisService;
import org.tmdrk.toturial.spring.service.IndexService;
import org.tmdrk.toturial.spring.service.redis.JedisService;
import org.tmdrk.toturial.spring.service.redis.RedisService;
import org.tmdrk.toturial.spring.service.vehicle.Vehicle;

import java.util.Arrays;
import java.util.Map;

/**
 * @ClassName Test
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/9 16:10
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext
                = new AnnotationConfigApplicationContext(App1Config.class);
        //获取操作系统名称
        ConfigurableEnvironment environment = annotationConfigApplicationContext.getEnvironment();
        System.out.println(environment.getProperty("os.name"));

        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(str->{
            System.out.println(str);      });

        Map<String, CodisService> beansOfType = annotationConfigApplicationContext.getBeansOfType(CodisService.class);
        beansOfType.forEach((key,value)->{
            System.out.println(key+"="+value);
        });

        System.out.println(annotationConfigApplicationContext.getBean(IndexService.class).getClass().getName());
        IndexService indexService = (IndexService)annotationConfigApplicationContext.getBean("indexService");
        indexService.query();

        RedisService redisService1 = (RedisService)annotationConfigApplicationContext.getBean("redisService");
        RedisService redisService2 = (RedisService)annotationConfigApplicationContext.getBean("redisService");
        System.out.println(redisService1==redisService2);

        JedisService jedisService = (JedisService)annotationConfigApplicationContext.getBean("jedisService");
        jedisService = (JedisService)annotationConfigApplicationContext.getBean("jedisService");


        MainConfigOfPropertyValues mainConfigOfPropertyValues = (MainConfigOfPropertyValues)annotationConfigApplicationContext.getBean("mainConfigOfPropertyValues");
        System.out.println(mainConfigOfPropertyValues);
        Vehicle vehicle = (Vehicle)annotationConfigApplicationContext.getBean("vehicle");
        System.out.println(vehicle);
    }
}
