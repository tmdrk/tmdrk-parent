package com.tmdrk.webwar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/7 21:19
 * @Version 1.0
 **/
@Controller
public class HelloController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("hello")
    public String hello(){
        logger.info("HelloController.hello......");
        return "hello";
    }
}
