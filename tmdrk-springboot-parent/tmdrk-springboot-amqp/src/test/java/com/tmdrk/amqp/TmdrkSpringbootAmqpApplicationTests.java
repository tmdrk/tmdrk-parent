package com.tmdrk.amqp;

import com.tmdrk.amqp.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TmdrkSpringbootAmqpApplicationTests {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void convertAndSend() {
        System.out.println("===========================================");
        //默认java序列化
        rabbitTemplate.convertAndSend("mydirect", "tmdrk.insert", new Book(23, "钢铁是怎样炼成的"));
        rabbitTemplate.convertAndSend("mydirect", "tmdrk.insert", new Book(23, "钢铁是怎样炼成的"));
        rabbitTemplate.convertAndSend("mydirect", "tmdrk.insert", new Book(23, "钢铁是怎样炼成的"));
        System.out.println("===========================================");
    }

    @Test
    public void receive() {
        System.out.println("===========================================");
        //默认java序列化
        Object msg = rabbitTemplate.receiveAndConvert("tmdrk.insert");
        System.out.println("class:" + msg.getClass());
        System.out.println("msg:" + msg);
        System.out.println("===========================================");
    }

    @Test
    public void convertAndSendFanout() {
        System.out.println("===========================================");
        //默认java序列化
        rabbitTemplate.convertAndSend("myfanout", "", new Book(23, "钢铁是怎样炼成的"));
        rabbitTemplate.convertAndSend("myfanout", "", new Book(23, "钢铁是怎样炼成的"));
        System.out.println("===========================================");
    }

    @Test
    public void createExchange() {
        System.out.println("===========================================");
        amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
        String queue = amqpAdmin.declareQueue(new Queue("amqpadmin.queue", true));//持久化
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE
                , "amqpadmin.exchange", "amqpadmin.queue", null));
        System.out.println("创建完成");
        System.out.println("===================================================================================================================================");
    }
}
