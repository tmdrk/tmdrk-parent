package org.tmdrk.toturial.spring.service.vehicle;

import org.springframework.beans.factory.annotation.Value;
import org.tmdrk.toturial.spring.service.IndexService;

/**
 * @ClassName Car
 * @Description 轿车
 * @Author zhoujie
 * @Date 2020/1/12 15:06
 * @Version 1.0
 **/
public class Car extends Vehicle{
    public Car(){
        System.out.println("car constructor...");
    }
    public void init(){
        System.out.println("car init...");
    }

    public void destory(){
        System.out.println("car destory...");
    }

}
