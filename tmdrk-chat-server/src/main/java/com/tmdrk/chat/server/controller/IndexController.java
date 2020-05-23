package com.tmdrk.chat.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/12 15:51
 * @Version 1.0
 **/
@Controller
public class IndexController {
    Logger logger = LoggerFactory.getLogger(IndexController.class);
    @ResponseBody
    @RequestMapping("/")
    public Map index(@RequestParam Map<String,Object> requestMap){
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("msg","欢迎光临");
        return retMap;
    }

    @ResponseBody
    @RequestMapping("/login")
    public Map login(@RequestParam Map<String,Object> requestMap){
        Map<String,Object> retMap = new HashMap<>();
        return retMap;
    }
}
