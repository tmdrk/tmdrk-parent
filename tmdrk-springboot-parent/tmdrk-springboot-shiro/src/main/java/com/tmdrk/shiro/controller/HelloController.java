package com.tmdrk.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName HelloController
 * @Description
 * @Author zhoujie
 * @Date 2020/5/14 17:24
 * @Version 1.0
 **/
@Controller
public class HelloController {
    Logger logger = LoggerFactory.getLogger(getClass());
    public static String PREFIX = "pages/";

    @RequestMapping("hello")
    public String hello(){
        logger.info("HelloController.hello...");
        return "hello";
    }

    @RequestMapping("login")
    public String login(){
        logger.info("HelloController.login...");
        return "login";
    }

    @RequestMapping("doLogin")
    public String doLogin(String userName,String password,Model model){
        logger.info("HelloController.doLogin...");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
//        token.setRememberMe(true);//设置记住我 与过滤器中的user类型配合使用
        try{
            model.addAttribute("name",userName);
            subject.login(token); //执行登录
            return "welcome";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名不存在");
            model.addAttribute("userName",userName);
            model.addAttribute("password",password);
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码不存在");
            model.addAttribute("userName",userName);
            model.addAttribute("password",password);
            return "login";
        }
    }


    @RequestMapping("/logout")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //session.removeAttribute("user");
//        return "login";
        return "redirect:/";
    }

    @ResponseBody
    @RequestMapping("noauth")
    public String noauth(){
        logger.info("HelloController.noauth...");
        return "未经授权无法访问此页面";
    }


    @RequestMapping({"/","/welcome"})
    public String welcome(Model model){
        logger.info("HelloController.welcome...");
        model.addAttribute("name","游客");
        return "welcome";
    }

    @RequestMapping("/level1/{path}")
    public String level1(@PathVariable("path")String path){
        return PREFIX+"level1/"+path;
    }
    @RequestMapping("/level1/{path}/{operate}")
    public String level1Add(@PathVariable("path")String path,@PathVariable("operate")String operate,Model model){
        model.addAttribute("operate",operate);
        return PREFIX+"level1/"+path+"-operate";
    }


    @RequestMapping("/level2/{path}")
    public String level2(@PathVariable("path")String path){
        return PREFIX+"level2/"+path;
    }
    @RequestMapping("/level2/{path}/{operate}")
    public String level2Add(@PathVariable("path")String path,@PathVariable("operate")String operate,Model model){
        model.addAttribute("operate",operate);
        return PREFIX+"level2/"+path+"-operate";
    }

}
