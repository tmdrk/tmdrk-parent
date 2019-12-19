package org.tmdrk.toturial.io.nio.socket;

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
                .addLast("lengthFieldBasedFrameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
                .addLast("lengthFieldPrepender",new LengthFieldPrepender(4))
                .addLast("stringDecoder",new StringDecoder(CharsetUtil.UTF_8))
                .addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8))
                .addLast("myClientHandler",new MyClientHandler());
    }
}
