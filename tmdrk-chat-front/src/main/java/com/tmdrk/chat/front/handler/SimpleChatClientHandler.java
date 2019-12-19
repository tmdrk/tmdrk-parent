package com.tmdrk.chat.front.handler;

import com.google.gson.reflect.TypeToken;
import com.tmdrk.chat.front.common.utils.GsonUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;

/**
 * @ClassName SimpleChatClientHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/8 23:43
 * @Version 1.0
 **/
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println(s);
    }


    public void register(ChannelHandlerContext channelHandlerContext, Map<String, Object> msg) {
        CacheLoader.channelMap.put(Double.parseDouble(msg.get("userId").toString()),channelHandlerContext.channel());
    }
    public void singleSend(ChannelHandlerContext channelHandlerContext, Map<String, Object> msg) {
        Double to = Double.parseDouble(msg.get("to").toString());
        msg.remove("to");
        msg.remove("type");
        CacheLoader.channelMap.get(to).writeAndFlush(new TextWebSocketFrame(GsonUtils.toJson(msg)));
    }
}
