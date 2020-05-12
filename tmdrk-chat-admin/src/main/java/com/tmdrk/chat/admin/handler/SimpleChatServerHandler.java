//package com.tmdrk.chat.admin.handler;
//
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.channel.group.ChannelGroup;
//import io.netty.channel.group.DefaultChannelGroup;
//import io.netty.util.concurrent.GlobalEventExecutor;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @ClassName SimpleChatServerHandler
// * @Description TODO
// * @Author zhoujie
// * @Date 2019/7/8 23:30
// * @Version 1.0
// **/
//public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {
//
//    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//    public static Map<Double,Channel> channelMap = new ConcurrentHashMap<>();
//
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
//        Channel incoming = ctx.channel();
//        for (Channel channel : channels) {
//            channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");
//        }
//        channels.add(ctx.channel());
//    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
//        Channel incoming = ctx.channel();
//        for (Channel channel : channels) {
//            channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 离开\n");
//        }
//        channels.remove(ctx.channel());
//    }
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception { // (4)
//        Channel incoming = ctx.channel();
//        for (Channel channel : channels) {
//            if (channel != incoming){
//                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
//            } else {
//                channel.writeAndFlush("[you]" + s + "\n");
//            }
//        }
//    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
//        Channel incoming = ctx.channel();
//        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"在线");
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
//        Channel incoming = ctx.channel();
//        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"掉线");
//    }
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
//        Channel incoming = ctx.channel();
//        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");
//        // 当出现异常就关闭连接
//        cause.printStackTrace();
//        ctx.close();
//    }
//}
