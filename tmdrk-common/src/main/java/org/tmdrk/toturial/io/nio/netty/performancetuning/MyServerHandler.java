package org.tmdrk.toturial.io.nio.netty.performancetuning;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @ClassName MyServerHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 12:43
 * @Version 1.0
 **/
@ChannelHandler.Sharable
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    public static final ChannelHandler INSTANCE = new MyServerHandler();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        ByteBuf data = Unpooled.directBuffer();
        data.writeBytes(msg);
        Object result = getResult(data);
        channelHandlerContext.writeAndFlush(result);
    }

    protected Object getResult(ByteBuf data) {
        // 80.0% = 1ms;
        // 90.0% = 10ms;
        // 97.0% = 100ms;
        // 99.9% = 1000ms;
        int level = ThreadLocalRandom.current().nextInt(1, 1000);
        int time;
        if(level<=900){
            time = 1;
        }else if(level<=950){
            time = 10;
        }else if(level<=990){
            time = 100;
        }else{
            time = 1000;
        }
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        super.handlerAdded(ctx);
    }
}
