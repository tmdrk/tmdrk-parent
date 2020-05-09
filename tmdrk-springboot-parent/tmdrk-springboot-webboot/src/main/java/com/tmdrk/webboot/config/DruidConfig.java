package com.tmdrk.webboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.sql.DataSource;

/**
 * @ClassName DruidConfig
 * @Description Druid配置类
 * @Author zhoujie
 * @Date 2020/5/4 19:31
 * @Version 1.0
 **/
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean statViewServlet(){
        // 进行druid 监控的配置处理.
        ServletRegistrationBean<Servlet> srb = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // 黑名单
        srb.addInitParameter("deny", "192.168.31.253");
        // 白名单
        srb.addInitParameter("allow", "127.0.0.1");//默认允许所有
        // 用户名
        srb.addInitParameter("loginUsername", "admin");
        // 密码
        srb.addInitParameter("loginPassword", "123456");
        // 是否可以重置数据源
        srb.addInitParameter("resetEnable", "false");
        return srb;
    }

    //配置web监控的filter
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> frb = new FilterRegistrationBean<>();
        frb.setFilter(new WebStatFilter());
        // 所有请求进行监控处理
        frb.addUrlPatterns("/*");
        // 排除名单
        frb.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,/druid/*");
        return frb;
    }
}
