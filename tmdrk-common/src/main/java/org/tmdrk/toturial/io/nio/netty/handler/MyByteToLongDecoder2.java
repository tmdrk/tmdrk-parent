package org.tmdrk.toturial.io.nio.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @ClassName MyByteToLongDecoder2
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/23 20:45
 * @Version 1.0
 **/
public class MyByteToLongDecoder2 extends ReplayingDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder2 decode invoked");
        out.add(in.readLong());
    }
}
