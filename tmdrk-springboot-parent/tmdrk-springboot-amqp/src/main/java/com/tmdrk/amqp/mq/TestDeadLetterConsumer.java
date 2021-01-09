package com.tmdrk.amqp.mq;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * TestDeadLetterConsumer
 *
 * @author Jie.Zhou
 * @date 2021/1/4 18:25
 */
@Component
public class TestDeadLetterConsumer implements InitializingBean {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${bargain.mq.testExpireQueue}")
    public  String testExpireQueue;

    @Value("${bargain.mq.testDeadLetterQueue}")
    public  String testDeadLetterQueue;

    @RabbitListener(queues = "${bargain.mq.testExpireQueue}", id = "qrcode-query")
    public void consumerOrder(@Payload String outTradeNo, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag,
                              @Header(value = "X-Retry", required = false, defaultValue = "1") Integer retryCount) {
        log.info("outTradeNo:{} deliveryTag:{} retryCount:{}",outTradeNo,deliveryTag,retryCount);
        rabbitTemplate.convertAndSend(testDeadLetterQueue, outTradeNo, msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(retryCount * 10000));
            msg.getMessageProperties().setHeader("X-Retry", retryCount + 1);
            return msg;
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Queue q = QueueBuilder.durable(testDeadLetterQueue)
                .withArgument("x-dead-letter-exchange", "amq.topic")
                .withArgument("x-dead-letter-routing-key", "qrcode").build();
        rabbitAdmin.declareQueue(q);

        Queue query = new Queue(testExpireQueue);
        rabbitAdmin.declareQueue(query);
        rabbitAdmin.declareBinding(BindingBuilder.bind(query).to(new TopicExchange("amq.topic")).with("qrcode"));
    }
}
