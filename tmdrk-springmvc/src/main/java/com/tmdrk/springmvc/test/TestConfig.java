package com.tmdrk.springmvc.test;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @ClassName AppConfig
 * @Description 测试配置类
 * @Author zhoujie
 * @Date 2020/4/28 17:32
 * @Version 1.0
 **/
@Configuration
@ComponentScan(value="com.tmdrk.springmvc")
@EnableWebMvc //开启springmvc定制配置功能  可以通过重写 WebMvcConfigurer 方法实现定制化功能
// 注意：此处与MyWebAppInitializer 的 ac.refresh();方法有冲突
public class TestConfig implements WebMvcConfigurer {
    //设置FastJson为json转换器，不配置返回json可能会报错
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new FastJsonHttpMessageConverter());
    }
}
