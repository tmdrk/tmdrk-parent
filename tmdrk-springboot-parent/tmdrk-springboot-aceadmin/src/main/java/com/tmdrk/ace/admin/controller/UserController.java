package com.tmdrk.ace.admin.controller;

import com.tmdrk.ace.admin.entity.AceUser;
import com.tmdrk.ace.admin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/4 23:14
 * @Version 1.0
 **/
@Controller
public class UserController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("user/{id}")
    public Object user(HttpServletRequest request, @PathVariable(value = "id")Integer id){
        logger.info("UserController.user()...");
        AceUser user = userService.getUser(id);
        return user;
    }

    @RequestMapping("/users")
    public String users(Model model){

        return "user";
    }
}
