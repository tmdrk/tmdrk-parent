package org.tmdrk.toturial.io.nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @ClassName ByteBufTest
 * @Description ByteBuf测试类
 * @Author zhoujie
 * @Date 2019/8/22 16:28
 * @Version 1.0
 **/
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(10);
        for(int i=0;i < 10;i++ ){
            byteBuf.writeByte(i);//绝对方法 会改变writeIndex
        }
        for(int i=0;i < 10;i++ ){
            System.out.println(byteBuf.getByte(i));//相对方法
        }
        while(byteBuf.isReadable()){
            System.out.println(byteBuf.readByte());//绝对方法 会改变readIndex
        }
    }
}
