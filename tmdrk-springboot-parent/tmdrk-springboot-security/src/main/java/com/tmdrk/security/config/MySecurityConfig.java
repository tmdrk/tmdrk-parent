package com.tmdrk.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName MySecurityConfig
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/11 1:30
 * @Version 1.0
 **/
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定制请求授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2");
        //开启自动配置的登录功能
        http.formLogin();
        //1 /login来到登录页
        //2 重定向到login/error表示登录失败
        //3 更多功能参考方法注释
    }


}
