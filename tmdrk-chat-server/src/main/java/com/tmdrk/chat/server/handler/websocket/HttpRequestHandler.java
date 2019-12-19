package com.tmdrk.chat.server.handler.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @ClassName HttpRequestHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/9 16:47
 * @Version 1.0
 **/
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    // webSocket标识
    private final String wsUri;

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        // 如果是webSocket请求，请求地址uri等于wsUri
        System.out.println("HttpRequestHandler.channelRead0");
        if (wsUri.equalsIgnoreCase(fullHttpRequest.uri())) {
            // 将消息发送到下一个channelHandler
            ctx.fireChannelRead(fullHttpRequest.retain());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}