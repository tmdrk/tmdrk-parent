package org.tmdrk.toturial.io.nio.netty.performancetuning;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
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
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap(); // (2)
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childOption(ChannelOption.SO_REUSEADDR,true)
//                    .handler(new LoggingHandler(LogLevel.INFO)) //handler-->bossGroup
                    .childHandler(new MyServerInitializer());
//            serverBootstrap.bind(8899).addListener((ChannelFutureListener) future->{
//                System.color.println("bind success in "+8899);
//            });
//            System.color.println("server started!");
//        }finally {
////            bossGroup.shutdownGracefully();
////            workerGroup.shutdownGracefully();
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
