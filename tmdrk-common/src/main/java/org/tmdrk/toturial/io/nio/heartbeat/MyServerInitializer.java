package org.tmdrk.toturial.io.nio.heartbeat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName WebSocketChannelInitializer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 14:52
 * @Version 1.0
 **/
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //空闲检测处理器
        pipeline.addLast(new IdleStateHandler(5,7,10, TimeUnit.SECONDS))
                .addLast(new MyServerHandler());
    }
}
