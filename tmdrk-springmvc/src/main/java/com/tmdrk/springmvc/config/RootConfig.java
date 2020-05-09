package com.tmdrk.springmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @ClassName RootConfig
 * @Description 父容器，只不扫描Controller
 * @Author zhoujie
 * @Date 2020/1/13 21:55
 * @Version 1.0
 **/
@ComponentScan(value="com.tmdrk.springmvc",excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
})
public class RootConfig {
}
