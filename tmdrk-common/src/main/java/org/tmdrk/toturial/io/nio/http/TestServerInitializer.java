package org.tmdrk.toturial.io.nio.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName TestServerInitializer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 2:51
 * @Version 1.0
 **/
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
        // 编解码http请求
        .addLast("httpServerCodec",new HttpServerCodec())
        .addLast("testHttpServerHandler",new TestHttpServerHandler());
    }
}
