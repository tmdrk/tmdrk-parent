package com.tmdrk.chat.server.handler.websocket;

import com.tmdrk.chat.common.cache.CacheLoader;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName ChatServer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/12 10:14
 * @Version 1.0
 **/
@Component
public class ChatServer extends Thread{
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ChatServerInitializer chatServerInitializer;

    @Override
    public void run() {
        try {
            runs();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("聊天服务启动异常");
        }
    }

    public void runs() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(chatServerInitializer)  //(4)
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            logger.info("ChatServer 启动了");

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(CacheLoader.TMDRK_CHAT_SERVER_PORT).sync(); // (7)

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("ChatServer 关闭了");
        }
    }

//    public static void start(){
//        try {
//            ChatServer.run();
//        } catch (Exception e) {
//            e.printStackTrace();
////            logger.error("netty聊天服务启动异常{}",e);
//        }
//    }
}
