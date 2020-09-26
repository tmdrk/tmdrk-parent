package com.tmdrk.ace.admin.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(Model model){
        String aa = "\\xF0\\x9F\\x92\\xA6";
        System.out.println(aa);
        return "success";
    }
}
