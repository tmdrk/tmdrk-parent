package com.tmdrk.chat.server.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @ClassName NettyApplicatonCloseEventListener
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/12 10:09
 * @Version 1.0
 **/
public class NettyApplicatonCloseEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("spring IOC is closed");
    }
}
