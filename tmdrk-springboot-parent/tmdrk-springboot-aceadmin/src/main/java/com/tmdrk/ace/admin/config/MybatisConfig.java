package com.tmdrk.ace.admin.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName MybatisConfig
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/8 23:05
 * @Version 1.0
 **/
@org.springframework.context.annotation.Configuration
public class MybatisConfig {
    //开启驼峰命名，也可以配置文件中配置 mybatis.mapUnderscoreToCamelCase=true 开启
//    @Bean
//    public ConfigurationCustomizer configurationCustomizer(){
//        return (configuration)->configuration.setMapUnderscoreToCamelCase(true);
//    }
}
