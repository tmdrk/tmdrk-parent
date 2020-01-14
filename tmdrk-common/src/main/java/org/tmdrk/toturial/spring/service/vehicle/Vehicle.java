package org.tmdrk.toturial.spring.service.vehicle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @ClassName Vehicle
 * @Description 车
 * 测试属性赋值
 * @Author zhoujie
 * @Date 2020/1/12 17:51
 * @Version 1.0
 **/
public class Vehicle {
    private Logger logger = Logger.getLogger(Vehicle.class);
    //使用@Value赋值
    //1 基本赋值
    //2 可以写SpEL；#{}
    //3 可以写${};取出配置文件中的值（在运行环境变量里面）

    @Value("奔驰")
    private String name;

    @Value("#{300000-20}")
    private Integer price;

    @Value("${nickName}")
    private String nickname;

    public Vehicle(){
        logger.info("Vehicle constructor...111");
        System.out.println("Vehicle constructor...");
    }
    public void init(){
        System.out.println("Vehicle init...");
    }

    public void destory(){
        System.out.println("Vehicle destory...");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
