package org.tmdrk.toturial.io.nio.netty.performancetuning;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName MyBusinessThreadPoolHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/27 17:25
 * @Version 1.0
 **/
public class MyBusinessThreadPoolHandler extends MyServerHandler{
    public static final MyBusinessThreadPoolHandler INSTANCE = new MyBusinessThreadPoolHandler();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(48);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        ByteBuf data = Unpooled.directBuffer();
        data.writeBytes(msg);
        threadPool.submit(()->{
            Object result = getResult(data);
            channelHandlerContext.writeAndFlush(result);
        });
    }
}
