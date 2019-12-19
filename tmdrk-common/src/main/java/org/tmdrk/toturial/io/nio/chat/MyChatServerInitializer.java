package org.tmdrk.toturial.io.nio.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.tmdrk.toturial.io.nio.socket.MyServerHandler;

/**
 * @ClassName MyChatServerInitializer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 13:35
 * @Version 1.0
 **/
public class MyChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast("delimiterBasedFrameDecoder", new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()))
                .addLast("stringDecoder",new StringDecoder(CharsetUtil.UTF_8))
                .addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8))
                .addLast("myChatServerHandler",new MyChatServerHandler());
    }
}
