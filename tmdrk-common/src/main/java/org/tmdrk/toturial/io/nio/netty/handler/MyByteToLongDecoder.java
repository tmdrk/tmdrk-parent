package org.tmdrk.toturial.io.nio.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName MyByteToLongDecoder
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/23 19:34
 * @Version 1.0
 **/
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode invoked!");
        System.out.println("readableBytes:"+in.readableBytes());
        if(in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
