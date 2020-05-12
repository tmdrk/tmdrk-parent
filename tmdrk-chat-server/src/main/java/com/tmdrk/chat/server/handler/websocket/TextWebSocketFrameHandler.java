package com.tmdrk.chat.server.handler.websocket;

import com.tmdrk.chat.common.cache.CacheLoader;
import com.tmdrk.chat.common.entity.Message;
import com.tmdrk.chat.common.utils.GsonUtils;
import com.tmdrk.chat.common.utils.JedisUtil;
import com.tmdrk.chat.common.utils.RedisUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TextWebSocketFrameHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/9 16:48
 * @Version 1.0
 **/
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static Logger logger = Logger.getLogger(TextWebSocketFrameHandler.class);
    private final ChannelGroup channelGroup;

    public TextWebSocketFrameHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.info("userEventTriggered开始...");
        // 如果ws握手完成
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            // 删除pipeLine中处理http请求的handler
            ctx.pipeline().remove(HttpRequestHandler.class);
            // 写一个消息广播到所有的客户端channel
            //channelGroup.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined!"));
            // 将当前客户端channel添加进group
            channelGroup.add(ctx.channel());
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
        // 关闭流
        ctx.channel().close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        logger.info("channelRead0开始...");
        // 将接收的消息通过ChannelGroup转发到所有连接的客户端
        //channelGroup.writeAndFlush(textWebSocketFrame.retain());
        // 前端组装的消息格式是 {"message":{"text":"项目地址","date":"2018-11-28T02:13:52.437Z"},"to":2,"from":1}
        Map<String,Object> msg = GsonUtils.fromJson(textWebSocketFrame.text().toString(),Map.class);
        String type = (String) msg.get("type");
        switch (type) {
            case "REGISTER":
                register(channelHandlerContext,msg);
                break;
            case "SINGLE_SENDING":
                singleSend(channelHandlerContext,msg);
                break;
        }
    }

    public void register(ChannelHandlerContext channelHandlerContext, Map<String, Object> msg) {
        logger.info("register开始...");
        logger.info("id:"+channelHandlerContext.channel().id()+" localAddress:"+channelHandlerContext.channel().localAddress());
        String bridge = CacheLoader.nettyIpAddr+":"+CacheLoader.nettyPort+"-"+channelHandlerContext.channel().id().toString();
        CacheLoader.channelMap.put(bridge,channelHandlerContext.channel());
        JedisUtil.set(CacheLoader.CHAT_USER_ID_PREFIX+msg.get("from").toString(),bridge);
    }

    public void singleSend(ChannelHandlerContext channelHandlerContext, Map<String, Object> msg) {
        logger.info("singleSend开始...");
        String to = msg.get("to").toString();
        String bridge = JedisUtil.get(CacheLoader.CHAT_USER_ID_PREFIX+to);
        String[] ipaddrAndId = bridge.split("-");
        if(ipaddrAndId[0].equals(CacheLoader.nettyIpAddr+":"+CacheLoader.nettyPort)) {
            //直接走本机
            if (bridge != null && CacheLoader.channelMap.containsKey(bridge) && CacheLoader.channelMap.get(bridge).isActive()) {
                ChannelFuture channelFuture = CacheLoader.channelMap.get(bridge).writeAndFlush(new TextWebSocketFrame(GsonUtils.toJson(msg)));
//            if(channelFuture.isSuccess()){
//                logger.info("发送成功");
//            }else{
//                logger.info("发送失败");
//            }
            } else {
                //存入缓存
                Message message = new Message();
                try {
                    BeanUtils.populate(message, msg);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                CacheLoader.put(message);
            }
        }else{
            //调用ipaddrAndId[0]对应的netty服务

        }
    }

}
