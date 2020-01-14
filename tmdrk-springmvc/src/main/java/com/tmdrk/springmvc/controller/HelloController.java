package com.tmdrk.springmvc.controller;

import com.tmdrk.springmvc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName HelloController
 * @Description hello
 * @Author zhoujie
 * @Date 2020/1/13 22:01
 * @Version 1.0
 **/
@Controller
public class HelloController {
    @Autowired
    HelloService helloService;

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return helloService.sayHello("zhoujie");
    }

    @RequestMapping("/suc")
    public String success(){
        return "success";
    }
}
