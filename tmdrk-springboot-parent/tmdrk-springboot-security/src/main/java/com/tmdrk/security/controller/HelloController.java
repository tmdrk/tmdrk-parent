package com.tmdrk.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/11 0:57
 * @Version 1.0
 **/
@Controller
public class HelloController {
    Logger logger = LoggerFactory.getLogger(getClass());
    public static String PREFIX = "pages/";

    @RequestMapping("hello")
    public String hello(){
        logger.info("HelloController.hello...");
        return "hello";
    }

    @RequestMapping("welcome")
    public String welcome(){
        logger.info("HelloController.welcome...");
        return "welcome";
    }

    @RequestMapping("/login")
    public String login(){
        logger.info("HelloController.login...");
        return PREFIX+"login";
    }

    @RequestMapping("doLogin")
    public String doLogin(){
        logger.info("HelloController.login...");
        return "forword:hello";
    }

    @RequestMapping("/level1/{path}")
    public String level1(@PathVariable("path")String path){
        return PREFIX+"level1/"+path;
    }
    @RequestMapping("/level2/{path}")
    public String level2(@PathVariable("path")String path){
        return PREFIX+"level2/"+path;
    }
}
