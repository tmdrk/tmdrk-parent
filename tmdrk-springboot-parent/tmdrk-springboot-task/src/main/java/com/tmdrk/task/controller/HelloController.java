package com.tmdrk.task.controller;

import com.tmdrk.task.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/10 23:23
 * @Version 1.0
 **/
@RestController
public class HelloController {
    @Autowired
    AsyncService asyncService;

    //异步处理
    @RequestMapping("hello")
    public String sayHello(){
        asyncService.hello();
        return "hello";
    }
}
