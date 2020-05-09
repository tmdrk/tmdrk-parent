package com.tmdrk.webboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName JdbcController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/8 17:17
 * @Version 1.0
 **/
@Controller
public class JdbcController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ResponseBody
    @RequestMapping("/jdbc/users")
    public Object getUser(){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT * FROM ACE_USER");
        return maps;
    }
}
