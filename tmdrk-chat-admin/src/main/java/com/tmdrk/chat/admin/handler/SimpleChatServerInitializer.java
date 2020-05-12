//package com.tmdrk.chat.admin.handler;
//
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.handler.codec.DelimiterBasedFrameDecoder;
//import io.netty.handler.codec.Delimiters;
//import io.netty.handler.codec.string.StringDecoder;
//import io.netty.handler.codec.string.StringEncoder;
//
//
///**
// * @ClassName SimpleChatServerInitializer
// * @Description TODO
// * @Author zhoujie
// * @Date 2019/7/8 23:32
// * @Version 1.0
// **/
//public class SimpleChatServerInitializer extends
//        ChannelInitializer<SocketChannel> {
//    @Override
//    public void initChannel(SocketChannel ch) throws Exception {
//        ChannelPipeline pipeline = ch.pipeline();
//
//        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
//        pipeline.addLast("decoder", new StringDecoder());
//        pipeline.addLast("encoder", new StringEncoder());
//        pipeline.addLast("handler", new SimpleChatServerHandler());
//
//        System.out.println("SimpleChatClient:"+ch.remoteAddress() +"连接上");
//    }
//}
