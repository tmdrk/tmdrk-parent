package org.tmdrk.toturial.io.nio.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @ClassName MyServerHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 14:57
 * @Version 1.0
 **/
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()){
                case READER_IDLE:eventType = "读空闲";break;
                case WRITER_IDLE:eventType = "写空闲";break;
                case ALL_IDLE:eventType = "读写空闲";break;
            }
            System.out.println(ctx.channel().remoteAddress()+"超时事件："+eventType);
            ctx.channel().close();
        }
    }
}
