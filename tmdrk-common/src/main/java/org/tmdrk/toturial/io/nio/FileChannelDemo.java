package org.tmdrk.toturial.io.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName FileChannelDemo
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/6 23:40
 * @Version 1.0
 **/
public class FileChannelDemo {
    public static void main(String[] args) throws Exception {
        //1.创建一个RandomAccessFile（随机访问文件）对象通过RandomAccessFile对象的getChannel()方法。
        RandomAccessFile raf=new RandomAccessFile("F:/dir1/demo1.txt","rw");

        FileChannel fc = raf.getChannel();

        //使用FileChannel的read()方法读取数据：
//        ByteBuffer byteBuffer= ByteBuffer.allocate(32);
//        int bys = fc.read(byteBuffer);

        ByteBuffer byteBuffer= ByteBuffer.allocate(1);
        raf.seek(0);
        int bys = fc.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        byteBuffer.clear();
        raf.seek(7);
        bys = fc.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        byteBuffer.clear();
        raf.seek(8);
        bys = fc.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        byteBuffer.clear();
        raf.seek(9);
        bys = fc.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        byteBuffer.clear();
        raf.seek(10);
        bys = fc.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));

        //使用FileChannel的write()方法写入数据：
//        ByteBuffer byteBuffer2=ByteBuffer.allocate(32);
//        byteBuffer2.put("hellos".getBytes());
//        byteBuffer2.flip();
//        fc.write(byteBuffer2);

//        ByteBuffer byteBuffer2=ByteBuffer.allocate(32);
//        raf.seek(32);
//        byteBuffer2.put(" ".getBytes());
//        byteBuffer2.flip();
//        fc.write(byteBuffer2);

//        raf.seek(raf.length());
//        raf.write("\n追加后的文本\n".getBytes());
//        raf.write("追加后的文本\n".getBytes());
        //3.关闭FileChannel
        fc.close();
    }
}
