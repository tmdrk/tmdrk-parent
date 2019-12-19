package org.tmdrk.toturial.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName MappedByteBufferDemo
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/14 11:29
 * @Version 1.0
 **/
public class MappedByteBufferDemo {

    public static void main(String[] args) throws IOException {
        RandomAccessFile raf =new RandomAccessFile("F:/dir1/demo1.txt","rw");
        FileChannel fc =raf.getChannel();
        MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_WRITE, 0, 10);
        buffer.put(1,(byte)'a');
        buffer.put(5,(byte)'c');
        raf.close();
    }
}
