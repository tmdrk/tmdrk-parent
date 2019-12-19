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
import org.tmdrk.toturial.io.nio.socket.MyClientHandler;
import org.tmdrk.toturial.io.nio.socket.MyServerHandler;

/**
 * @ClassName MyChatClientInitializer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 13:58
 * @Version 1.0
 **/
public class MyChatClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast("delimiterBasedFrameDecoder", new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()))
                .addLast("stringDecoder",new StringDecoder(CharsetUtil.UTF_8))
                .addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8))
                .addLast("myClientHandler",new MyChatClientHandler());
    }
}
