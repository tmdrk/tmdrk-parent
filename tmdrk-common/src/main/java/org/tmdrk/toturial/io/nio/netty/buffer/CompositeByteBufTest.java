package org.tmdrk.toturial.io.nio.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @ClassName CompositeByteBufTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/23 0:14
 * @Version 1.0
 **/
public class CompositeByteBufTest {
    public static void main(String[] args) {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        ByteBuf head = Unpooled.buffer(10);
        head.writeBytes("asdfghjkl".getBytes());
        ByteBuf body = Unpooled.directBuffer(20);
        body.writeBytes("1234567891234567890".getBytes());
        compositeByteBuf.addComponents(head,body);
        compositeByteBuf.forEach(System.out::println);
    }
}
