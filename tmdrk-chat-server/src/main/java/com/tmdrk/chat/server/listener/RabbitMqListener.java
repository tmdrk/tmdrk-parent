package com.tmdrk.chat.server.listener;

import com.tmdrk.chat.common.cache.CacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.util.Map;

/**
 * @ClassName RabbitMqListener
 * @Description 监听器配置在 META-INF/spring.factories 中
 * @Author zhoujie
 * @Date 2020/5/12 4:20
 * @Version 1.0
 **/
public class RabbitMqListener implements ApplicationListener<ApplicationStartedEvent> {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("RabbitMqListener.监听到应用开始...");
        Map<String, AmqpAdmin> beansOfType = event.getApplicationContext().getBeansOfType(AmqpAdmin.class);
        AmqpAdmin amqpAdmin = beansOfType.get("amqpAdmin");
        if(amqpAdmin!=null){
            //创建交换器
            amqpAdmin.declareExchange(new DirectExchange(CacheLoader.TMDRK_CHAT_EXCHANGE));
            //创建队列
            String queue = amqpAdmin.declareQueue(new Queue(CacheLoader.TMDRK_CHAT_QUEUE_NAME, true));//持久化
            //交换器与队列做绑定
            amqpAdmin.declareBinding(new Binding(CacheLoader.TMDRK_CHAT_QUEUE_NAME,Binding.DestinationType.QUEUE
                    ,CacheLoader.TMDRK_CHAT_EXCHANGE,CacheLoader.TMDRK_CHAT_QUEUE_NAME,null));
            logger.info("队列创建完成");
        }else{
            throw new RuntimeException("队列创建失败，因为AmqpAdmin为null");
        }
    }
}
