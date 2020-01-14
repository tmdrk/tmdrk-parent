package org.tmdrk.toturial.io.nio.netty.millionconnection;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName MyServerHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 12:43
 * @Version 1.0
 **/
@Sharable
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    private AtomicInteger atomicInteger = new AtomicInteger();


    public  MyServerHandler(){
        System.out.println("MyServerHandler 实例化方法调用");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            System.out.println(this+" connections:"+atomicInteger.get());
        },0,2, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.color.println("channelActive");
        atomicInteger.incrementAndGet();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        System.color.println("channelInactive");
        atomicInteger.decrementAndGet();
        super.channelInactive(ctx);
    }


}
