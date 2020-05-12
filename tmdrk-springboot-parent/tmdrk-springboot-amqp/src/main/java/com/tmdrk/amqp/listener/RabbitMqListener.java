package com.tmdrk.amqp.listener;

import com.tmdrk.amqp.util.IpUtil;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.util.Map;

/**
 * @ClassName RabbitMqListener
 * @Description
 * @Author zhoujie
 * @Date 2020/5/12 4:20
 * @Version 1.0
 **/
public class RabbitMqListener implements ApplicationListener<ApplicationStartedEvent> {
    private static final String QUEUE_NAME="tmdrk-chat-queue-"+ IpUtil.getIpAddress();

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("RabbitMqListener.监听到应用开始...");
        Map<String, AmqpAdmin> beansOfType = event.getApplicationContext().getBeansOfType(AmqpAdmin.class);
        AmqpAdmin amqpAdmin = beansOfType.get("amqpAdmin");
        if(amqpAdmin!=null){
            amqpAdmin.declareExchange(new DirectExchange("tmdrk.chat.exchange"));
            String queue = amqpAdmin.declareQueue(new Queue("QUEUE_NAME", true));//持久化
            amqpAdmin.declareBinding(new Binding(QUEUE_NAME,Binding.DestinationType.QUEUE
                    ,"tmdrk.chat.exchange",QUEUE_NAME,null));
            System.out.println("创建完成");
        }
    }
}
