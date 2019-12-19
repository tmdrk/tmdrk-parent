package org.tmdrk.toturial.io.nio.netty.stickyunpacking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @ClassName MyClientHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 13:12
 * @Version 1.0
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer,Charset.forName("utf-8"));
        System.out.println("来自于服务器的消息长度："+buffer.length);
        System.out.println("来自于服务器的消息内容："+message);
        System.out.println("来自于服务器的消息数量："+(++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<200;i++){
            ByteBuf byteBuf = Unpooled.copiedBuffer("send from client1", Charset.forName("utf-8"));
            ctx.writeAndFlush(byteBuf);
        }
        ctx.writeAndFlush("来自于客户端的消息");
    }
}
