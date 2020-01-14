package org.tmdrk.toturial.spring.service.vehicle;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.POST;

/**
 * @ClassName Jeep
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/12 15:36
 * @Version 1.0
 **/
@Service
public class Jeep extends Vehicle{
    public Jeep(){
        System.out.println("Jeep constructor...");
    }

    @PostConstruct
    public void init(){
        System.out.println("Jeep init...");
    }

    @PreDestroy
    public void destory(){
        System.out.println("Jeep destory...");
    }
}
