package com.tmdrk.chat.front.service;

import com.tmdrk.chat.front.common.utils.GsonUtils;
import com.tmdrk.chat.front.handler.CacheLoader;
import com.tmdrk.chat.front.handler.SimpleChatClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @ClassName SimpleChatClientServiceImpl
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/8 23:44
 * @Version 1.0
 **/
public class SimpleChatClientServiceImpl implements SimpleChatClientService{
    public static void main(String[] args) throws Exception{
        new SimpleChatClientServiceImpl("localhost", 8080).run();
    }

    private final String host;
    private final int port;

    public SimpleChatClientServiceImpl(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap  = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer());
            Channel channel = bootstrap.connect(host, port).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }

}
