package com.tmdrk.chat.server.listener;

import com.tmdrk.chat.common.cache.CacheLoader;
import com.tmdrk.chat.server.handler.websocket.ChatServer;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @ClassName NettyApplicatonStartEventListener
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/12 10:08
 * @Version 1.0
 **/
public class NettyApplicatonStartEventListener implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("Spring start successful");
        CacheLoader.initSeq();
        ChatServer.start();
    }
}
