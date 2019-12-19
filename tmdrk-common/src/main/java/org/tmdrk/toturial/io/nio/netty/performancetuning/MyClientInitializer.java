package org.tmdrk.toturial.io.nio.netty.performancetuning;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * @ClassName MyClientInitializer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 13:10
 * @Version 1.0
 **/
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new FixedLengthFrameDecoder(Long.BYTES))
                .addLast(MyClientHandler.INSTANCE);
    }
}
