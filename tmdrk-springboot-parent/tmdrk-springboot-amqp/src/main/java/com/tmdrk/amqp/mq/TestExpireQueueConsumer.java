package com.tmdrk.amqp.mq;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
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
import java.io.IOException;

/**
 * TestExpireQueueConsumer
 *
 * @author Jie.Zhou
 * @date 2021/1/5 9:11
 */
@Component
public class TestExpireQueueConsumer implements InitializingBean {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${bargain.mq.testExpireQueue}")
    public  String testExpireQueue;

    @RabbitListener(queues = "${bargain.mq.testExpireQueue}", id = "expire-query")
    public void consumerOrder(@Payload String outTradeNo, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag,
                              @Header(value = "X-Retry", required = false, defaultValue = "1") Integer retryCount) throws IOException {
        log.info("outTradeNo:{} deliveryTag:{} retryCount:{}",outTradeNo,deliveryTag,retryCount);
        try{
            if(retryCount < 10){
                throw new RuntimeException("未达到次数异常");
            }
            log.info("任务完成");
            channel.basicAck(deliveryTag, false);
        }catch (Exception e){
            log.error("任务失败，消息回队 retryCount:{}",retryCount);
            channel.basicNack(deliveryTag,false,true);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        rabbitAdmin.declareQueue(new Queue(testExpireQueue));
    }
}
