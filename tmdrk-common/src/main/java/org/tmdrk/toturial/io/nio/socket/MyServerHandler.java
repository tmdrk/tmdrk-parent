package org.tmdrk.toturial.io.nio.socket;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName MyServerHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 12:43
 * @Version 1.0
 **/
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(48);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(channelHandlerContext.channel().remoteAddress()+", "+s);
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<100000;i++){
            stringBuffer.append(UUID.randomUUID());
        }
        threadPool.execute(()->{
            channelHandlerContext.writeAndFlush("from server:" + stringBuffer.toString()).addListener(future -> {
                Thread.sleep(3000);
                if(future.isDone()&&future.isSuccess()){
                    System.out.println("server write is success "+",currentThread="+Thread.currentThread());
                }else {
                    System.out.println("server write is fail "+",currentThread="+Thread.currentThread());
                }
            });
            System.out.println("future thread finished "+",currentThread="+Thread.currentThread());
        });
        System.out.println("channelRead0 finished "+",currentThread="+Thread.currentThread());
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
