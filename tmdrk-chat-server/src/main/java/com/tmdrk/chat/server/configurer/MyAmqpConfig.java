package com.tmdrk.chat.server.configurer;

import com.tmdrk.chat.common.cache.CacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
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

    @Bean
    public MessageConverter messageConverter(){
       return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queue(){
        return new Queue(CacheLoader.TMDRK_CHAT_QUEUE_NAME,true);
    }

}
