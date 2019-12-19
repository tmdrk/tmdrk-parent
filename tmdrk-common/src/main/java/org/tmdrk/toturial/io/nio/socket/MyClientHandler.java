package org.tmdrk.toturial.io.nio.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @ClassName MyClientHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 13:12
 * @Version 1.0
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    int count=0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//        int level = ThreadLocalRandom.current().nextInt(1, 100);
//        if(level<=20){
//            throw new RuntimeException("模拟异常");
//        }
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client output:"+msg);
        ctx.writeAndFlush("from client:"+ LocalDateTime.now());
        System.out.println("=================================================:"+(++count));
        Thread.sleep(1000);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("来自于客户端的消息");
    }
}
