package com.tmdrk.jpa.controller;

import com.tmdrk.jpa.entity.UserInfo;
import com.tmdrk.jpa.repository.UserInfoRepository;
import com.tmdrk.jpa.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @ClassName UserInfoController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/9 1:04
 * @Version 1.0
 **/
@RestController
public class UserInfoController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserInfoRepository userInfoRepository;

    @GetMapping("user/info/{id}")
    public ResultUtil.Result getUserInfo(@PathVariable("id") Integer id){
        Optional<UserInfo> optional = userInfoRepository.findById(id);
        if(!optional.isPresent()){
            return ResultUtil.fail("查询为空");
        }
        return ResultUtil.success(optional.get());
    }

    @PostMapping("user/info")
    public ResultUtil.Result insertUserInfo(UserInfo userInfo){
        UserInfo save = userInfoRepository.save(userInfo);
        return ResultUtil.success(save);
    }
}
