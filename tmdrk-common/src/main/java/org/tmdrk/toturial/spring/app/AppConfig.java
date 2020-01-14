package org.tmdrk.toturial.spring.app;

import org.springframework.context.annotation.*;
import org.tmdrk.toturial.spring.condition.LinuxCondition;
import org.tmdrk.toturial.spring.condition.MyImportSelector;
import org.tmdrk.toturial.spring.condition.WindowsCondition;
import org.tmdrk.toturial.spring.service.color.Color;
import org.tmdrk.toturial.spring.service.redis.CodisService;
import org.tmdrk.toturial.spring.service.redis.JedisService;
import org.tmdrk.toturial.spring.service.redis.RedisService;

/**
 * @ClassName AppConfig
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/9 16:08
 * @Version 1.0
 **/
@Configuration //没有该配置将会使用原生AppConfig类，初始化bean；如果有该配置，将会使用代理类初始化bean相关配置
//包扫描，将含有spring注解的相关class都纳入到spring容器管理
//@ComponentScan(value="org.tmdrk.toturial.spring",excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class,Service.class})
//})
@ComponentScan(value="org.tmdrk.toturial.spring",includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Service.class}),
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {IndexService.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM,classes = {MyTypeFilter.class})
},useDefaultFilters=false)
@Import({Color.class, MyImportSelector.class}) //给容器中快速导入组件，支持多个
public class AppConfig {

//    @Scope(value="prototype")
    @Bean
    public RedisService redisService(){
        System.out.println("创建redisService bean...");
        return new RedisService();
    }
    @Lazy
    @Bean
    public JedisService jedisService(){
        System.out.println("创建jedisService bean...");
        return new JedisService();
    }

    /*
     * @Author zhoujie
     * @Description 按照一定的条件进行判断，满足该条件给容器中注册该bean
     * @Date 10:28 2020/1/11
     * @Param []
     * @return org.tmdrk.toturial.spring.service.redis.CodisService
     **/
    @Conditional(WindowsCondition.class) //该注释还可以放在类上，效果是一样的，可以配置不同环境加载不同配置文件。
    @Bean
    public CodisService codisService01(){
        System.out.println("创建codisService01 bean...");
        return new CodisService();
    }
    @Conditional(LinuxCondition.class) //测试Linux环境可设置启动参数 -Dos.name=Linux
    @Bean
    public CodisService codisService02(){
        System.out.println("创建codisService02 bean...");
        return new CodisService();
    }

}
