package com.tmdrk.servlet3;

import javax.servlet.*;
import java.io.IOException;

/**
 * @ClassName HelloFilter
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/9 13:18
 * @Version 1.0
 **/
public class HelloFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //过滤请求
        System.out.println("HelloFilter...doFilter...");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
