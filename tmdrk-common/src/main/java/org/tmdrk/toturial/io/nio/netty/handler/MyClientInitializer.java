package org.tmdrk.toturial.io.nio.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

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
                .addLast(new MyByteToLongDecoder2())
                .addLast(new MyLongToByteEncoder())
                .addLast("myClientHandler",new MyClientHandler());
    }
}
