package com.tmdrk.webboot.controller;

import com.tmdrk.webboot.exception.UserNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/7 1:29
 * @Version 1.0
 **/
@Controller
public class UserController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/user")
    @ResponseBody
    public String user(@RequestParam("user")String user){
        logger.info("UserController.user()...");
        if("aaa".equals(user)){
            throw new UserNotExistException();
        }
        return "success";
    }
}
