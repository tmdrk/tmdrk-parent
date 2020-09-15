package com.tmdrk.amqp.service;

import com.tmdrk.amqp.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName BookService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/10 17:48
 * @Version 1.0
 **/
@Service
public class BookService {
    @Autowired
    private AmqpAdmin amqpAdmin;

    Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues="tmdrk.update")
    public void receive01(Book book){
        logger.info("=================>> receive01 收到消息:{}",book);
    }

    @RabbitListener(queues="tmdrk.insert")
    public void receive02(Message message){
        logger.info("=================>> receive02 收到消息:{} properties:{}",message.getBody(),message.getMessageProperties());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author zhoujie
     * @Description
     *  监听动态队列
     *  Spring SPEL表达式,获取bean对象的值
     *  queue是在MyAmqpconfig配置中赋值的bean，由监听器中创建的同名队列
     * @Date 5:10 2020/5/12
     * @Param [message]
     * @return void
     **/
    @RabbitListener(queues = "#{queue.name}")
    public void process(Message message){
        logger.info("=================>> process 收到消息:{} ",message);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
