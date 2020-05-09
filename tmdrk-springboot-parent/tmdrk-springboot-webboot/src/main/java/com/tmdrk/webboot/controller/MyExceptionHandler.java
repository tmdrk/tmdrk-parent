package com.tmdrk.webboot.controller;

import com.tmdrk.webboot.exception.UserNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MyExceptionHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/7 1:37
 * @Version 1.0
 **/
@ControllerAdvice
public class MyExceptionHandler {

    //浏览器服务器返回的都是json
//    @ResponseBody
//    @ExceptionHandler(UserNotExistException.class)
//    public Map<String,Object> handleException(Exception e){
//        Map<String,Object> map = new HashMap<>();
//        map.put("code","user not exist");
//        map.put("message",e.getMessage());
//        return map;
//    }

    @ExceptionHandler(UserNotExistException.class)
    public String handleException(Exception e, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("code","user not exist");
        map.put("message","用户不存在异常");
        //BasicErrorController Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        request.setAttribute("javax.servlet.error.status_code",401);

        //与MyErrorAttributes配合使用，扩展json异常参数
        request.setAttribute("ext",map);
        return "forward:/error";
    }
}
