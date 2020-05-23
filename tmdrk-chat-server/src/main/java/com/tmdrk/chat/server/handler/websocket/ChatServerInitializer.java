package com.tmdrk.chat.server.handler.websocket;

import com.tmdrk.chat.common.cache.CacheLoader;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName ChatServerInitializer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/9 16:52
 * @Version 1.0
 **/
@Component
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    TextWebSocketFrameHandler textWebSocketFrameHandler;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
                // 编解码http请求
                .addLast(new HttpServerCodec())
                //聚合解码HttpRequest/HttpContent/LastHttpContent到FullHttpRequest
                //保证接收的Http请求的完整性
                .addLast(new HttpObjectAggregator(64 * 1024))
                // 处理FullHttpRequest
                .addLast(new HttpRequestHandler(CacheLoader.TMDRK_CHAT_HTTP_WS))
                // 处理其他的WebSocketFrame
                .addLast(new WebSocketServerProtocolHandler(CacheLoader.TMDRK_CHAT_HTTP_WS))
                // 处理TextWebSocketFrame
                .addLast(textWebSocketFrameHandler);
    }
}
