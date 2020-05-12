package com.tmdrk.my.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyAutoConfiguration
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/9 21:13
 * @Version 1.0
 **/
@Configuration
@ConditionalOnWebApplication //web应用才生效
@EnableConfigurationProperties({MyProperties.class})
public class MyAutoConfiguration {

    @Bean
    public MyService myService(MyProperties myProperties){
        MyService myService = new MyService();
        myService.setMyProperties(myProperties);
        return myService;
    }
}
