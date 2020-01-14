package com.tmdrk.chat.front.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/8 21:39
 * @Version 1.0
 **/
@Controller
//@RequestMapping("/index")
public class IndexController {
    private Logger logger = Logger.getLogger(IndexController.class);
    @RequestMapping("/index")
    public String index(){
            logger.info("欢迎来到前台主页");
        return "index";
    }
}
