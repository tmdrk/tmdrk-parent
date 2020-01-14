package com.tmdrk.springmvc.service;

import org.springframework.stereotype.Service;

/**
 * @ClassName HelloService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/13 22:02
 * @Version 1.0
 **/
@Service
public class HelloService {
    public String sayHello(String name){
        return "Hello "+name;
    }
}
