package com.tmdrk.webboot.config;

import com.tmdrk.webboot.servlet.MyServlet;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyServerConfig
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/7 2:48
 * @Version 1.0
 **/
@Configuration
public class MyServerConfig {
    //定制嵌入式的tomcat容器相关规则
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>(){
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                factory.setPort(8090);
            }
        };
    }

    //注册三大组件
    @Bean
    public ServletRegistrationBean myServlet(){
        return new ServletRegistrationBean(new MyServlet(),"/myServlet");
    }
}
