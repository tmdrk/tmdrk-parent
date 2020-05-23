package com.tmdrk.chat.server.handler.websocket;

import com.alibaba.fastjson.JSON;
import com.tmdrk.chat.common.cache.CacheLoader;
import com.tmdrk.chat.common.utils.GsonUtils;
import com.tmdrk.chat.common.utils.JedisUtil;
import com.tmdrk.chat.common.utils.StringUtil;
import com.tmdrk.chat.dao.entity.ChatUserMessage;
import com.tmdrk.chat.server.service.ChatUserMessageService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName TextWebSocketFrameHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/9 16:48
 * @Version 1.0
 **/
@Component
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ChatUserMessageService chatUserMessageService;

    @Autowired
    RabbitTemplate rabbitTemplate;

//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        logger.info("userEventTriggered开始...");
//        // 如果ws握手完成
//        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
//            // 删除pipeLine中处理http请求的handler
//            ctx.pipeline().remove(HttpRequestHandler.class);
//
//            // 写一个消息广播到所有的客户端channel
//            //channelGroup.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined!"));
//            // 将当前客户端channel添加进group
//            //channelGroup.add(ctx.channel()); //此处暂时没什么用，所以注释
//
//            //从获取好友列表，遍历，从channelMap中获取上线好友的channel，给好友发上线提醒消息
//
//        } else {
//            super.userEventTriggered(ctx, evt);
//        }
//    }
//
//    /*
//     * channelAction
//     * channel 通道 action 活跃的
//     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
//     */
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) {
//        System.out.println(ctx.channel().localAddress().toString() + " 通道已激活！");
//    }
//
//    /*
//     * channelInactive
//     * channel 通道 Inactive 不活跃的
//     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
//     */
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) {
//        System.out.println(ctx.channel().localAddress().toString() + " 通道不活跃！");
//        // 关闭流
//        ctx.channel().close();
//    }
//
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
//        logger.info("channelRead0开始...");
//        // 将接收的消息通过ChannelGroup转发到所有连接的客户端
//        //channelGroup.writeAndFlush(textWebSocketFrame.retain());
//        // 前端组装的消息格式是 {"message":{"text":"项目地址","date":"2018-11-28T02:13:52.437Z"},"to":2,"from":1}
//        Map<String,Object> msg = GsonUtils.fromJson(textWebSocketFrame.text().toString(),Map.class);
//        String type = (String) msg.get("type");
//        switch (type) {
//            case "REGISTER":
//                register(channelHandlerContext,msg);
//                break;
//            case "SINGLE_SENDING":
//                singleSend(channelHandlerContext,msg);
//                break;
//        }
//    }
//
//    public void register(ChannelHandlerContext channelHandlerContext, Map<String, Object> msg) {
//        logger.info("register开始...");
//        logger.info("id:"+channelHandlerContext.channel().id()+" localAddress:"+channelHandlerContext.channel().localAddress());
//        String bridge = CacheLoader.nettyIpAddr+":"+CacheLoader.nettyPort+"-"+channelHandlerContext.channel().id().toString();
//        CacheLoader.channelMap.put(bridge,channelHandlerContext.channel());
//        JedisUtil.set(CacheLoader.CHAT_USER_ID_PREFIX+msg.get("from").toString(),bridge);
//    }
//
//    public void singleSend(ChannelHandlerContext channelHandlerContext, Map<String, Object> msg) {
//        logger.info("singleSend开始...");
//        String to = msg.get("to").toString();
//        String bridge = JedisUtil.get(CacheLoader.CHAT_USER_ID_PREFIX+to);
//        String[] ipaddrAndId = bridge.split("-");
//        if(ipaddrAndId[0].equals(CacheLoader.nettyIpAddr+":"+CacheLoader.nettyPort)) {
//            //直接走本机
//            if (bridge != null && CacheLoader.channelMap.containsKey(bridge) && CacheLoader.channelMap.get(bridge).isActive()) {
//                ChannelFuture channelFuture = CacheLoader.channelMap.get(bridge).writeAndFlush(new TextWebSocketFrame(GsonUtils.toJson(msg)));
////            if(channelFuture.isSuccess()){
////                logger.info("发送成功");
////            }else{
////                logger.info("发送失败");
////            }
//            } else {
//                //存入缓存
//                Message message = new Message();
//                try {
//                    BeanUtils.populate(message, msg);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//                CacheLoader.put(message);
//            }
//        }else{
//            //调用ipaddrAndId[0]对应的netty服务
//
//        }
//    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.info("userEventTriggered开始...");
        // 如果ws握手完成
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            // 删除pipeLine中处理http请求的handler
            ctx.pipeline().remove(HttpRequestHandler.class);

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /*
     * channelAction
     * channel 通道 action 活跃的
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().localAddress().toString() + " 通道已激活！");
    }

    /*
     * channelInactive
     * channel 通道 Inactive 不活跃的
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().localAddress().toString() + " 通道不活跃！");
        //删除用户channel缓存，用户服务缓存
        String userId = CacheLoader.channelUserMap.get(ctx.channel());
        if(!StringUtil.isEmpty(userId)){
            CacheLoader.userChannelMap.remove(userId);
        }
        CacheLoader.channelUserMap.remove(ctx.channel());
        // 关闭流
        ctx.channel().close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        logger.info("channelRead0开始...");
        // 将接收的消息通过ChannelGroup转发到所有连接的客户端
        //channelGroup.writeAndFlush(textWebSocketFrame.retain());
        // 前端组装的消息格式是 {"message":{"text":"项目地址","date":"2018-11-28T02:13:52.437Z"},"to":2,"from":1}
//        Map<String,Object> msg = GsonUtils.fromJson(textWebSocketFrame.text().toString(),Map.class);
        Map<String,Object> msg = JSON.parseObject(textWebSocketFrame.text(),Map.class);
        String type = (String) msg.get("type");
        switch (type) {
            case "0":
                register(channelHandlerContext,msg);
                break;
            case "1":
                singleSend(channelHandlerContext,msg);
                break;
        }
    }

    public void register(ChannelHandlerContext channelHandlerContext, Map<String, Object> msg) {
        logger.info("register开始...");
        logger.info("id:"+channelHandlerContext.channel().id()+" localAddress:"+channelHandlerContext.channel().localAddress());

        //缓存用户与channel
        CacheLoader.userChannelMap.put(msg.get("from").toString(),channelHandlerContext.channel());
        CacheLoader.channelUserMap.put(channelHandlerContext.channel(),msg.get("from").toString());
        //缓存用户与netty服务器ip地址对应关系
        JedisUtil.set(CacheLoader.TMDRK_CHAT_SERVER_USERID_PRFIX+msg.get("from").toString(),CacheLoader.LOCAL_IP_ADDR);

        //从获取好友列表，遍历，从channelMap中获取上线好友的channel，给好友发上线提醒消息
//        List<String> friends = new ArrayList<>();
//        friends.stream().filter(f->CacheLoader.userChannelMap.containsKey(f)).forEach(f->{
//            Channel channel = CacheLoader.userChannelMap.get(f);
//            Map<String, Object> tipMsg = new HashMap<>();
//            tipMsg.put("from",msg.get("from"));
//            tipMsg.put("to",f);
//            tipMsg.put("message","我上线了");
//            tipMsg.put("type","SINGLE_SENDING");
//            channel.writeAndFlush(new TextWebSocketFrame(GsonUtils.toJson(tipMsg)));
//        });
    }

    public void singleSend(ChannelHandlerContext channelHandlerContext, Map<String, Object> msg) {
        logger.info("singleSend开始...");
        String toIp = JedisUtil.get(CacheLoader.TMDRK_CHAT_SERVER_USERID_PRFIX + msg.get("to").toString());
        if(!StringUtil.isEmpty(toIp)){
            //如果接收者在线，获取该用户对应的netty服务器队列，将消息发送到队列里，由对应服务器消费
            //将消息发送到接收者所在的服务器队列里，由对应服务器消费
            rabbitTemplate.convertAndSend(CacheLoader.TMDRK_CHAT_EXCHANGE,CacheLoader.TMDRK_CHAT_QUEUE_PRFIX+toIp, msg);
            logger.info("消息发送完成,exchange={},queue={}",CacheLoader.TMDRK_CHAT_EXCHANGE,CacheLoader.TMDRK_CHAT_QUEUE_PRFIX+toIp);
        }else{
            //如果接收者不在线，则把消息存入离线消息表
            logger.info("用户["+msg.get("to")+"]离线,消息入库");
            ChatUserMessage record = new ChatUserMessage();
            record.setFromId(Integer.parseInt(msg.get("from").toString()));
            record.setToId(Integer.parseInt(msg.get("to").toString()));
            record.setMessage(msg.get("message").toString());
            record.setStatus((byte)0);
            record.setMessageType(Byte.parseByte(msg.get("type").toString()));
            Long time = System.currentTimeMillis() / 1000;
            record.setCreateTime(time.intValue());
            chatUserMessageService.insertSelective(record);
        }
    }

}
