package com.tmdrk.myboot.controller;

import com.tmdrk.my.autoconfigure.MyService;
import com.tmdrk.myboot.entity.Home;
import com.tmdrk.myboot.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HelloWorld
 * @Description hello test
 * @Author zhoujie
 * @Date 2020/4/29 21:48
 * @Version 1.0
 **/
@RestController
public class HelloWorldController {
    @Autowired
    Person person;
    @Autowired
    ApplicationContext context; //注入ioc容器

    @Autowired
    MyService myService;

    @GetMapping("/hello")
    public Map hello(){
        System.out.println("HelloWorld.hello...");
        System.out.println(person.toString());
        Map map = new HashMap();
        map.put("name","lion");
        map.put("person",person.toString());
        map.put("person1",person);
        map.put("age",person.getAge());
        map.put("email",getString(person));
        System.out.println(context.getBean("animal"));
        return map;
    }
    public String getString(Person person){
        return person.getEmail();
    }

    @RequestMapping("say/hello")
    public String sayHello(String name){
        System.out.println("=================");
        return myService.sayHello(name);
    }
}
