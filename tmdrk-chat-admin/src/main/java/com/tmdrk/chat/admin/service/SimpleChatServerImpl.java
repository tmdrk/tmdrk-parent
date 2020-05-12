//package com.tmdrk.chat.admin.service;
//
//import com.tmdrk.chat.admin.handler.SimpleChatServerInitializer;
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//
///**
// * @ClassName SimpleChatServerImpl
// * @Description TODO
// * @Author zhoujie
// * @Date 2019/7/8 23:39
// * @Version 1.0
// **/
//public class SimpleChatServerImpl implements SimpleChatServer{
//
//    private int port = 8888;
//
//    public SimpleChatServerImpl(int port) {
//        this.port = port;
//    }
//
//    public void runChat() throws Exception {
//
//        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            ServerBootstrap b = new ServerBootstrap(); // (2)
//            b.group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class) // (3)
//                    .childHandler(new SimpleChatServerInitializer())  //(4)
//                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
//
//            System.out.println("SimpleChatServer 启动了");
//
//            // 绑定端口，开始接收进来的连接
//            ChannelFuture f = b.bind(port).sync(); // (7)
//
//            // 等待服务器  socket 关闭 。
//            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
//            f.channel().closeFuture().sync();
//
//        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
//
//            System.out.println("SimpleChatServer 关闭了");
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        int port;
//        if (args.length > 0) {
//            port = Integer.parseInt(args[0]);
//        } else {
//            port = 8080;
//        }
//        new SimpleChatServerImpl(port).runChat();
//
//    }
//}
