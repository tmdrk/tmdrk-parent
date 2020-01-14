package com.tmdrk.servlet3.service;

/**
 * @ClassName OneHelloServiceImpl
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/13 20:32
 * @Version 1.0
 **/
public class OneHelloServiceImpl implements HelloService{
    @Override
    public void sayHello() {
        System.out.println("OneHelloServiceImpl hello");
    }
}
