package com.tmdrk.webwar;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * @Author zhoujie
     * @Description 该方法重写了SpringBootServletInitializer  configure 方法，高数构建器主程序入口
     * @Date 22:42 2020/5/7
     * @Param [application]
     * @return org.springframework.boot.builder.SpringApplicationBuilder
     **/
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TmdrkSpringbootWebwarApplication.class);
    }

}
