package com.tmdrk.amqp.controller;

import com.alibaba.fastjson.JSON;
import com.tmdrk.amqp.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName BargainController
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/9/15 23:55
 * @Version 1.0
 **/
@RestController
public class BargainController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Value("${bargain.mq.queueBargainItemStore}")
    public String queueBargainItemStore;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @GetMapping("/queue")
    public Map test(){
        for(int i=0;i<10;i++){
            //routingKey 代表队列
            rabbitTemplate.convertAndSend(
                    queueBargainItemStore,
                    JSON.toJSONString(new Book(i, "钢铁是怎样炼成的"))
            );
        }
        //默认java序列化
        log.info("===========================================");
        Map<String,Object> map = new HashMap();
        map.put("code",200);
        map.put("queue",200);
        return map;
    }

    @GetMapping("/direct")
    public Map direct(){
        for(int i=0;i<10;i++){
            //routingKey 不能直接指向队列
            rabbitTemplate.convertAndSend(
                    "test.direct",
                    "direct",
                    JSON.toJSONString(new Book(i, "钢铁是怎样炼成的"))
            );
        }
        //默认java序列化
        log.info("===========================================");
        Map<String,Object> map = new HashMap();
        map.put("code",200);
        map.put("direct",200);
        return map;
    }
}
