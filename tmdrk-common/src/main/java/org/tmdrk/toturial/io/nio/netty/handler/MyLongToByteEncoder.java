package org.tmdrk.toturial.io.nio.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ClassName MyLongToByteEncoder
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/23 19:45
 * @Version 1.0
 **/
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("encode invoked!");
        System.out.println("msg:"+msg);
        out.writeLong(msg);
    }
}
