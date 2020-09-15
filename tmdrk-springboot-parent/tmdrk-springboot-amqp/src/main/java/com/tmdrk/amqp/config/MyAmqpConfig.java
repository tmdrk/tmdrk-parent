package com.tmdrk.amqp.config;

import com.tmdrk.amqp.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyAmqpConfig
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/10 17:38
 * @Version 1.0
 **/
@Configuration
public class MyAmqpConfig {
    Logger logger = LoggerFactory.getLogger(getClass());
    private static final String QUEUE_NAME="tmdrk-chat-queue-"+ IpUtil.getIpAddress();

    @Bean
    public MessageConverter messageConverter(){
       return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME,true);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
