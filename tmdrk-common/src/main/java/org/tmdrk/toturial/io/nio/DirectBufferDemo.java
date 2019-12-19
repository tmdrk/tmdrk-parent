package org.tmdrk.toturial.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName DirectBufferDemo
 * @Description 直接缓存（堆外内存） 实现零拷贝
 * @Author zhoujie
 * @Date 2019/8/14 10:54
 * @Version 1.0
 **/
public class DirectBufferDemo {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("F:/dir1/demo1.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("F:/dir1/demo2.txt",true);
        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();
        System.out.println();
        ByteBuffer buffer = ByteBuffer.allocateDirect(8);
        buffer.put("\n".getBytes());
        buffer.flip();
        outputChannel.write(buffer);
        while(true){
            buffer.clear();
            int read = inputChannel.read(buffer);
            System.out.println(" read:"+read);
            if(read == -1){
                break;
            }
            buffer.flip();
            outputChannel.write(buffer);
        }
        inputChannel.close();
        outputChannel.close();
    }
}
