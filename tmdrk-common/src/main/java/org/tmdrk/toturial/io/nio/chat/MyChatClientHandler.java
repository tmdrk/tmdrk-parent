package org.tmdrk.toturial.io.nio.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName MyChatClientHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 14:00
 * @Version 1.0
 **/
public class MyChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println(msg);
    }
}
