package com.tmdrk.webboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/2 4:57
 * @Version 1.0
 **/
@Controller
public class HelloController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        logger.info("HelloController.hello()...");
        return "success";
    }

}
