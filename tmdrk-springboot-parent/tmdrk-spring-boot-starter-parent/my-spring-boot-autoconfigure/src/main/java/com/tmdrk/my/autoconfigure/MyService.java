package com.tmdrk.my.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName MyService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/9 21:10
 * @Version 1.0
 **/
public class MyService {

    MyProperties myProperties;

    public String sayHello(String name){
        String autoString = myProperties.getPrefix() + "-" + name + "-" + myProperties.getSuffix();
        return autoString;
    }

    public MyProperties getMyProperties() {
        return myProperties;
    }

    public void setMyProperties(MyProperties myProperties) {
        this.myProperties = myProperties;
    }
}
