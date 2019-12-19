package org.tmdrk.toturial.io.nio.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ByteBufTest1
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/22 20:47
 * @Version 1.0
 **/
public class ByteBufTest1 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("123456789", Charset.forName("utf-8"));
        System.out.println(byteBuf.refCnt());
        byteBuf.retain();
        byteBuf.retain();
        System.out.println(byteBuf.refCnt());
        if (byteBuf.hasArray()) {//true 表示堆上缓存
            byte[] bytes = byteBuf.array();
            System.out.println(new String(bytes, Charset.forName("utf-8")) + ";");
            System.out.println(byteBuf);
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            System.out.println(byteBuf.readableBytes());
        }
        byteBuf.writeBytes("you should know,people born balance ,people born freedom,people born power,people born fair".getBytes());
//        byteBuf.release();
        if (byteBuf.hasArray()) {//true 表示堆上缓存
            byte[] bytes = byteBuf.array();
            System.out.println(new String(bytes, Charset.forName("utf-8")) + ";");
            System.out.println(byteBuf);
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            System.out.println(byteBuf.readableBytes());
        }
        System.out.println(byteBuf.getCharSequence(0, byteBuf.readableBytes(), Charset.forName("utf-8")));

        ByteBuf byteBuf1 = Unpooled.copiedBuffer(new byte[]{0,0,0,127});
        System.out.println(byteBuf1);
        int i1 = byteBuf1.readableBytes();
        byteBuf1.markReaderIndex();
        int length = byteBuf1.readInt();
        int i2 = byteBuf1.readableBytes();
        System.out.println(i1+","+i2+","+length);
        byteBuf1.resetReaderIndex();
    }
}
