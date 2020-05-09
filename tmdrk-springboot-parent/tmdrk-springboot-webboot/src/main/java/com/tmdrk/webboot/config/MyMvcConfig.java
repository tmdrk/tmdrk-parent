package com.tmdrk.webboot.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName MyMvcConfig
 * @Description
 * If you want to keep Spring Boot MVC features and you want to add additional MVC configuration
 * (interceptors, formatters, view controllers, and other features), you can add your own @Configuration
 * class of type WebMvcConfigurer but without @EnableWebMvc
 * 若你既想保持springboot MVC的特性，又想添加MVC配置，可以自定义配置类加@Configuration注解，而且不能使用@EnableWebMvc注解
 * @Author zhoujie
 * @Date 2020/5/4 15:41
 * @Version 1.0
 **/
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/test").setViewName("error");
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/login.html").setViewName("login");
            }
        };
        return webMvcConfigurer;
    }
}
