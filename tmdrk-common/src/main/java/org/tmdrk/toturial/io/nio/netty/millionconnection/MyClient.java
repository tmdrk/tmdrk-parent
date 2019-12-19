package org.tmdrk.toturial.io.nio.netty.millionconnection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ClassName MyClient
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 12:46
 * @Version 1.0
 **/
public class MyClient {
    public static void main(String[] args) throws Exception {
        int startPort = 7000;
        int range = 100;

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new MyClientHandler());

            int index = 0;
            int port;
            int channelcount = 0;
            while(!Thread.interrupted()){
                channelcount++;
                port = startPort+index;
                try {
                    ChannelFuture channelFuture = bootstrap.connect("local73", port).sync();
                    channelFuture.addListener(future -> {
                        if (!future.isSuccess()) {
                            System.out.println("connect failed,exit!");
                            System.exit(0);
                        }
                    });
                    channelFuture.get();
                }catch (Exception e){

                }
                if(++index == range){
                    index = 0;
                }
                System.out.println("channelcount:"+channelcount);
                if(channelcount!=0&&channelcount%200000==0){
                    System.out.println("休眠60秒");
                    Thread.sleep(60000);
                }
            }


        }finally {
//            eventLoopGroup.shutdownGracefully();
        }
    }
}
