package com.tmdrk.ace.admin.controller;

import com.tmdrk.ace.admin.annotationMapper.AnnotationAceUserMapper;
import com.tmdrk.ace.admin.entity.AceUser;
import com.tmdrk.ace.admin.service.UserService;
import com.tmdrk.ace.admin.util.ResultUtil;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @ClassName AnnotationAceUserController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/8 18:12
 * @Version 1.0
 **/
@RestController
public class AnnotationAceUserController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AnnotationAceUserMapper annotationAceUserMapper;

    @GetMapping("ace/user/{id}")
    public Object aceUser(@PathVariable(value = "id")Integer id){
        logger.info("AnnotationAceUserController.aceUser()...");
        AceUser aceUser = annotationAceUserMapper.selectById(id);
        return ResultUtil.success(aceUser);
    }

    @DeleteMapping("ace/delete/{id}")
    public Object aceDelete(@PathVariable(value = "id")Integer id){
        logger.info("AnnotationAceUserController.aceDelete()...");
        int result = annotationAceUserMapper.deleteById(id);
        if(result==0){
            return ResultUtil.fail("删除失败");
        }
        return ResultUtil.success();
    }

    @PostMapping("ace/user")
    public Object addAceUser(AceUser aceUser){
        logger.info("AnnotationAceUserController.aceUser()...");
        aceUser.setUserName("\\xF0\\x9F\\x92\\xA6");
        try {
            String encode = URLEncoder.encode(aceUser.getUserName(), "utf-8");
            System.out.println(encode);
            String decode = URLDecoder.decode(encode, "utf-8");
            System.out.println(decode);
        } catch (UnsupportedEncodingException e) {

        }
        int result = annotationAceUserMapper.insert(aceUser);
        if(result==0){
            return ResultUtil.fail("新增失败");
        }
        return ResultUtil.success();
    }

    @PutMapping("ace/user")
    public Object updateAceUser(AceUser aceUser){
        logger.info("AnnotationAceUserController.updateAceUser()...");
        int result = annotationAceUserMapper.update(aceUser);
        if(result==0){
            return ResultUtil.fail("更新失败");
        }
        return ResultUtil.success();
    }
}
