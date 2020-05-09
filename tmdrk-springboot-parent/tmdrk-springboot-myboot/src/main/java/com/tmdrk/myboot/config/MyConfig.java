package com.tmdrk.myboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.Random;

/**
 * @ClassName MyConfig
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/4/29 21:52
 * @Version 1.0
 **/
@Configuration
@ImportResource(locations = {"classpath:bean.xml"})//@ImportResource导入xml资源
public class MyConfig {

}
