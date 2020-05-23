package com.tmdrk.chat.server.listener;

import com.tmdrk.chat.common.cache.CacheLoader;
import com.tmdrk.chat.common.utils.JedisUtil;
import com.tmdrk.chat.server.handler.websocket.ChatServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.util.Map;

/**
 * @ClassName NettyApplicatonStartEventListener
 * @Description 启动netty服务 监听器配置在 META-INF/spring.factories 中
 * @Author zhoujie
 * @Date 2019/7/12 10:08
 * @Version 1.0
 **/
public class NettyApplicatonStartEventListener implements ApplicationListener<ApplicationStartedEvent> {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        logger.info("聊天服务启动中...");
        Map<String, ChatServer> beansOfType = event.getApplicationContext().getBeansOfType(ChatServer.class);
        ChatServer chatServer = beansOfType.get("chatServer");
        try {
            chatServer.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("聊天服务启动异常");
        }
        //将服务名存入缓存，以便客户端连接时获取服务名连接
        JedisUtil.setSet(CacheLoader.TMDRK_CHAT_SERVER_IPS,CacheLoader.LOCAL_IP_ADDR+":"+CacheLoader.TMDRK_CHAT_SERVER_PORT+CacheLoader.TMDRK_CHAT_HTTP_WS);
    }
}
