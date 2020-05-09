package org.tmdrk.toturial.spring.app;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.tmdrk.toturial.spring.condition.LinuxCondition;
import org.tmdrk.toturial.spring.condition.MyImportBeanDefinitionRegistrar;
import org.tmdrk.toturial.spring.condition.MyImportSelector;
import org.tmdrk.toturial.spring.condition.WindowsCondition;
import org.tmdrk.toturial.spring.service.color.ColorFactoryBean;
import org.tmdrk.toturial.spring.service.redis.CodisService;
import org.tmdrk.toturial.spring.service.color.Color;
import org.tmdrk.toturial.spring.service.redis.JedisService;
import org.tmdrk.toturial.spring.service.redis.RedisService;

/**
 * @ClassName App1Config
 * @Description TODO
 *
 *  给容器中注册组件的方式
 *  1 包扫描+组件注释
 *  2 @Bean[导入第三方包使用]
 *  3 @Import[快速的给容器中导入组件]
 *       @Import  输入要导入的组件，默认id是全类名
 *       ImportSelector  返回需要导入组件的全类名数组
 *       ImportBeanDefinitionRegistrar
 *  4 使用spring提供的FactoryBean
 *       默认获取到的是工场bean调用getObject方法返回的对象
 *       要获取工场bean本身，我们需要给id前面加前缀&
 *
 * @Author zhoujie
 * @Date 2020/1/12 13:38
 * @Version 1.0
 **/
@Configuration //没有该配置将会使用原生AppConfig类，初始化bean；如果有该配置，将会使用代理类初始化bean相关配置
//包扫描，将含有spring注解的相关class都纳入到spring容器管理
@ComponentScan(value="org.tmdrk.toturial.spring.controller",excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {ComponentScan.class})
})
@Import({Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class}) //给容器中快速导入组件，支持多个
@ImportResource
public class App1Config {
    private RedisService redisService;

    @Bean
    public RedisService redisService(){
        System.out.println("创建redisService bean...");
        return new RedisService();
    }

    @Scope(value="prototype")
    @Bean
    public Color color(){
        System.out.println("创建coler bean...");
        return new Color();
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

    @Bean
    public ColorFactoryBean colorFactoryBean(){
        System.out.println("创建colorFactoryBean bean...");
        return new ColorFactoryBean();
    }

    @Component
    class Pay{

    }
}

