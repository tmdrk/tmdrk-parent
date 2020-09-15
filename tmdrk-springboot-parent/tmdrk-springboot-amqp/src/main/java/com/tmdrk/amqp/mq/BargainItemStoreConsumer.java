package com.tmdrk.amqp.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.tmdrk.amqp.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * BargainItemStoreConsumer
 * 商品库存更新
 * @author Jie.Zhou
 * @date 2020/9/15 11:27
 */
@Component
public class BargainItemStoreConsumer implements InitializingBean {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Value("${bargain.mq.queueBargainItemStore}")
    public  String                 queueBargainItemStore;

    // 测试轮询分发 [0,2,4,6,8]
    @RabbitListener(queues = "${bargain.mq.queueBargainItemStore}")
    public void process1(@Payload String msg, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        log.info("mq订单消费1:{}", msg);
        Book book = null;
        try {
            Thread.sleep(1000);
            book = JSON.parseObject(msg, Book.class);
            int a=1/0;
            Long deliveryTag = (Long)headers.get("amqp_deliveryTag");
            channel.basicAck(deliveryTag, false);
        } catch (Exception ignore) {
            log.info("MQ订单对象转换异常1{}", msg);
            return;
        }finally {
        }
    }
    // 测试轮询分发 [1,3,5,7,9]
    @RabbitListener(queues = "${bargain.mq.queueBargainItemStore}")
    public void process2(@Payload String msg, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        log.info("mq订单消费2:{}", msg);
        Book book = null;
        try {
            book = JSON.parseObject(msg, Book.class);
            int a=1/0;
            Long deliveryTag = (Long)headers.get("amqp_deliveryTag");
            channel.basicAck(deliveryTag, false);
        } catch (Exception ignore) {
            log.info("MQ订单对象转换异常2{}", msg);
            return;
        }finally {
        }
    }


    // 测试公平分发 [0,2]
    @RabbitListener(queues = "${bargain.mq.queueBargainItemStore}")
    public void fair1(@Payload String msg, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        log.info("mq订单消费1:{}", msg);
        Book book = null;
        try {
            Thread.sleep(500);
            book = JSON.parseObject(msg, Book.class);
        } catch (Exception ignore) {
            log.info("MQ订单对象转换异常{}", msg);
            return;
        }
    }
    // 测试公平分发 [1,3,4,5,6,7,8,9]
    @RabbitListener(queues = "${bargain.mq.queueBargainItemStore}")
    public void fair2(@Payload String msg, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        log.info("mq订单消费2:{}", msg);
        Book book = null;
        try {
            book = JSON.parseObject(msg, Book.class);
        } catch (Exception ignore) {
            log.info("MQ订单对象转换异常{}", msg);
            return;
        }
    }


    // 测试direct路由 [1]
    @RabbitListener(queues = "test.direct.queue1")
    public void direct1(@Payload String msg, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        log.info("mq订单消费1:{}", msg);
        Book book = null;
        try {
            Thread.sleep(800);
            book = JSON.parseObject(msg, Book.class);
        } catch (Exception ignore) {
            log.info("MQ订单对象转换异常{}", msg);
            return;
        }
    }
    // 测试direct路由 [0,2,3,4,5,6,7,8,9]
    @RabbitListener(queues = "test.direct.queue2")
    public void direct2(@Payload String msg, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        log.info("mq订单消费2:{}", msg);
        Book book = null;
        try {
            book = JSON.parseObject(msg, Book.class);
        } catch (Exception ignore) {
            log.info("MQ订单对象转换异常{}", msg);
            return;
        }finally {
            Long deliveryTag = (Long)headers.get("amqp_deliveryTag");
            channel.basicAck(deliveryTag, false);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //队列轮询/公平分发   new Queue(queueBargainItemStore)默认持久化
        rabbitAdmin.declareQueue(new Queue(queueBargainItemStore));
        //直接路由
        rabbitAdmin.declareExchange(new DirectExchange("test.direct", false, false));
        rabbitAdmin.declareQueue(new Queue("test.direct.queue1", false));
        rabbitAdmin.declareQueue(new Queue("test.direct.queue2", false));
        rabbitAdmin.declareBinding(new Binding("test.direct.queue1",
                Binding.DestinationType.QUEUE,
                "test.direct", "direct", new HashMap<>()));
        rabbitAdmin.declareBinding(new Binding("test.direct.queue2",
                Binding.DestinationType.QUEUE,
                "test.direct", "direct", new HashMap<>()));
        //匹配路由
        rabbitAdmin.declareExchange(new TopicExchange( "test.topic",  false, false));
        rabbitAdmin.declareQueue(new Queue("test.topic.queue",  false));
        rabbitAdmin.declareBinding(
                BindingBuilder
                        .bind(new Queue("test.topic.queue", false))     //直接创建队列
                        .to(new TopicExchange("test.topic", false, false))  //直接创建交换机 建立关联关系
                        .with("user.#"));   //指定路由Key
        //广播
        rabbitAdmin.declareExchange(new FanoutExchange("test.fanout", false, false));
        rabbitAdmin.declareQueue(new Queue("test.fanout.queue", false));
        rabbitAdmin.declareBinding(
                BindingBuilder
                        .bind(new Queue("test.fanout.queue", false))
                        .to(new FanoutExchange("test.fanout", false, false)));
        //清空队列数据
//        rabbitAdmin.purgeQueue("test.topic.queue", false);
    }
}
