package org.tmdrk.toturial.io.nio.netty.millionconnection;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName MyClientHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 13:12
 * @Version 1.0
 **/
@Sharable
public class MyClientHandler extends ChannelInboundHandlerAdapter {
    public MyClientHandler(){
        System.out.println("MyClientHandler 实例化方法调用");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush("来自于客户端的消息");
    }
}
