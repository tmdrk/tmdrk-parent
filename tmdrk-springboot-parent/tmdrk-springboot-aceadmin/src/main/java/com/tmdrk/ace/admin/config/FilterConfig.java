//package com.tmdrk.ace.admin.config;
//
//import com.tmdrk.ace.admin.LogFilter.LogFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Arrays;
//
///**
// * @ClassName FilterConfig
// * @Description 过滤器
// * @Author zhoujie
// * @Date 2020/5/5 17:11
// * @Version 1.0
// **/
//@Configuration
//public class FilterConfig {
//
//    @Bean
//    public FilterRegistrationBean logFilter() {
//        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        final LogFilter logFilter = new LogFilter();
//        filterRegistrationBean.setFilter(logFilter);
//        filterRegistrationBean.setUrlPatterns(Arrays.asList("/**"));
//        return filterRegistrationBean;
//    }
//}
