package com.tmdrk.servlet3.service;

/**
 * @ClassName TwoHelloServiceImpl
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/13 20:32
 * @Version 1.0
 **/
public class TwoHelloServiceImpl implements HelloService{
    @Override
    public void sayHello() {
        System.out.println("TwoHelloServiceImpl hello");
    }
}
