package org.tmdrk.toturial.io.nio.netty.performancetuning;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * @ClassName WebSocketChannelInitializer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 12:38
 * @Version 1.0
 **/
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    EventLoopGroup businessGroup = new NioEventLoopGroup(1000);
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new FixedLengthFrameDecoder(Long.BYTES))
//                .addLast(businessGroup,MyServerHandler.INSTANCE);
//                .addLast(MyServerHandler.INSTANCE);
                .addLast(MyBusinessThreadPoolHandler.INSTANCE);
    }
}
