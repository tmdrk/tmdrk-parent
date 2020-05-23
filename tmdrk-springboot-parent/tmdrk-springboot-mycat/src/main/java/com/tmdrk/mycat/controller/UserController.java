package com.tmdrk.mycat.controller;

import com.tmdrk.mycat.entity.User;
import com.tmdrk.mycat.service.UserService;
import com.tmdrk.mycat.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/18 18:54
 * @Version 1.0
 **/
@RestController
public class UserController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserService userService;

    @RequestMapping("user/{id}")
    public ResultUtil.Result user(@PathVariable("id")Integer id){
        logger.info("UserController.user...");
        User user = userService.getUser(id);
        return ResultUtil.success(user);
    }

    @RequestMapping("fm/user/{id}")
    public ResultUtil.Result userForceMaster(@PathVariable("id")Integer id){
        logger.info("UserController.userForceMaster...");
        User user = userService.getUserForceMaster(id);
        return ResultUtil.success(user);
    }

    @RequestMapping("user/add")
    public ResultUtil.Result userAdd(User user){
        logger.info("UserController.userAdd...");
        int result = userService.userAdd(user);
        if(result==1){
            return ResultUtil.success();
        }
        return ResultUtil.fail("插入失败");
    }
}
