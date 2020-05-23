package com.tmdrk.chat.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(value={"com.tmdrk.chat.dao.mapper"})
@ComponentScan("com.tmdrk.chat")
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ServerApplication.class);
//        application.addListeners(new NettyApplicatonStartEventListener());
//        application.addListeners(new RabbitMqListener());
        application.run(args);
    }

}
