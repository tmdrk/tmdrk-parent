package com.tmdrk.springmvc.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.tmdrk.springmvc.controller.MyFirstInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @ClassName AppConfig
 * @Description 子容器，只扫描Controller
 * @Author zhoujie
 * @Date 2020/1/13 21:55
 * @Version 1.0
 **/
@ComponentScan(value="com.tmdrk.springmvc",includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
},useDefaultFilters = false)
@EnableWebMvc //开启springmvc定制配置功能  可以通过重写WebMvcConfigurer方法实现定制化功能
public class AppConfig implements WebMvcConfigurer {

    //试图解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/",".jsp");
    }

    //静态资源访问
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyFirstInterceptor()).addPathPatterns("/**");
    }

    //设置FastJson为json转换器，不配置返回json可能会报错
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new FastJsonHttpMessageConverter());
    }
}
