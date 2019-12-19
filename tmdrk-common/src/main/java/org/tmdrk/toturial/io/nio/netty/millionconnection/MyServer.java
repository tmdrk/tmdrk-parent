package org.tmdrk.toturial.io.nio.netty.millionconnection;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @ClassName MyServer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 12:36
 * @Version 1.0
 **/
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        int startPort = 7000;
        int range = 100;

        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap(); // (2)
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
//                    .handler(new LoggingHandler(LogLevel.INFO)) //handler-->bossGroup
                    .childHandler(new MyServerHandler());
            for(int i=0;i < range;i++){
                int port = startPort + i;
                serverBootstrap.bind(port).addListener(future -> {
                    System.out.println("bind success in "+port);
                });
            }
            System.out.println("server started!");
        }finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
        }
    }
}
