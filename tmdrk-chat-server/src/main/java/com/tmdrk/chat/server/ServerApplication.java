package com.tmdrk.chat.server;

import com.tmdrk.chat.server.listener.NettyApplicatonStartEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ServerApplication.class, args);
        SpringApplication application = new SpringApplication(ServerApplication.class);
//        application.addListeners(new ApplicationListener<ApplicationStartedEvent>() {
//            @Override
//            public void onApplicationEvent(ApplicationStartedEvent event) {
//                //do some thing
//            }
//        });
        application.addListeners(new NettyApplicatonStartEventListener());
        application.run(args);
    }

}
