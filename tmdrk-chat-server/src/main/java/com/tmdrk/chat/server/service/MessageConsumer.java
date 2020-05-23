package com.tmdrk.chat.server.service;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP;
import com.tmdrk.chat.common.cache.CacheLoader;
import com.tmdrk.chat.common.utils.IpUtils;
import com.tmdrk.chat.dao.entity.ChatUserMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName BookService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/10 17:48
 * @Version 1.0
 **/
/**
 * @Author zhoujie
 * @Description 消费者
 * @Date 19:59 2020/5/12
 * @Param
 * @return
 **/
@Service
public class MessageConsumer {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ChatUserMessageService chatUserMessageService;


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
    public void process(AMQP.Channel channel, Message message){
        logger.info("=================>> process 收到消息:{} ",message);
        Map body = JSON.parseObject(message.getBody(), Map.class);
        Channel nettyChannel = CacheLoader.userChannelMap.get(body.get("to").toString());
        if(nettyChannel!=null&&nettyChannel.isActive()){
            ChannelFuture channelFuture = nettyChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(body)));
            logger.info("channelFuture:"+channelFuture);
        }else{
            logger.info("用户["+body.get("to")+"]离线,消息入库");
            ChatUserMessage record = new ChatUserMessage();
            record.setFromId(Integer.parseInt(body.get("from").toString()));
            record.setToId(Integer.parseInt(body.get("to").toString()));
            record.setMessage(body.get("message").toString());
            record.setMessageType(Byte.parseByte(body.get("type").toString()));
            record.setStatus((byte)0);
            Long time = System.currentTimeMillis() / 1000;
            record.setCreateTime(time.intValue());
            chatUserMessageService.insertSelective(record);
        }
    }
}
