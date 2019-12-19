package org.tmdrk.toturial.io.nio.netty.stickyunpacking;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @ClassName WebSocketChannelInitializer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 12:38
 * @Version 1.0
 **/
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast("myServerHandler",new MyServerHandler());
    }
}
