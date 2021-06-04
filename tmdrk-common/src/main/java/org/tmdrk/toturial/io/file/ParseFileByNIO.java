package org.tmdrk.toturial.io.file;

import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * ParseFileByNIO
 *
 * @author Jie.Zhou
 * @date 2021/5/8 17:30
 */
public class ParseFileByNIO {
    public static void main(String[] args) throws Exception {
        String filePath = "D:\\tmp\\file\\io-test\\parse.txt";
//        readFixedFile(filePath);
        System.out.println("=============");
        readLineFileNio(filePath);
        System.out.println("-------------");
        readLineFileIo(filePath);
        System.out.println("=============");
    }

    private static void readLineFileIo(String filePath) throws IOException {
        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = new FileInputStream(filePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str;
        while((str = bufferedReader.readLine()) != null) {
            System.out.println(str);
        }
        //close
        inputStream.close();
        bufferedReader.close();
    }

    private static void readLineFileNio(String filePath) throws IOException {
        //1.创建一个RandomAccessFile（随机访问文件）对象通过RandomAccessFile对象的getChannel()方法。
        RandomAccessFile raf = new RandomAccessFile(filePath,"rw");
        FileChannel fc = raf.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        int bytesRead  = fc.read(buffer);
        while(bytesRead  != -1){
            // System.out.println("读取字节数："+bytesRead);
            //之前是写buffer，现在要读buffer
            buffer.flip();// 切换模式，写->读
            while (buffer.hasRemaining()) {
                byte b = buffer.get();
                if (b == 10 || b == 13) { // 换行或回车
                    byteBuffer.flip();
                    // 这里就是一个行
                    if(byteBuffer.limit() > 0){
                        final String line = Charset.forName("utf-8").decode(byteBuffer).toString();
                        if(StringUtils.hasText(line)){
                            System.out.println(line);// 解码已经读到的一行所对应的字节
                        }
                    }
                    byteBuffer.clear();
                } else {
                    if (!byteBuffer.hasRemaining()) {
                        // 空间不够扩容
                        byteBuffer = reAllocate(byteBuffer);
                    }
                    byteBuffer.put(b);
                }
            }
            System.out.println(Charset.forName("utf-8").decode(byteBuffer).toString());// 解码已经读到的一行所对应的字节
            buffer.clear();// 清空,position位置为0，limit=capacity
            //  继续往buffer中写
            bytesRead  = fc.read(buffer);
            fc.close();
        }
    }

    private static void readFixedFile(String filePath) throws IOException {
        //1.创建一个RandomAccessFile（随机访问文件）对象通过RandomAccessFile对象的getChannel()方法。
        RandomAccessFile raf = new RandomAccessFile(filePath,"rw");
        FileChannel fc = raf.getChannel();
        ByteBuffer byteBuffer= ByteBuffer.allocate(2);
        int bytesRead  = fc.read(byteBuffer);
        while(bytesRead  != -1){
            // System.out.println("读取字节数："+bytesRead);
            //之前是写buffer，现在要读buffer
            byteBuffer.flip();// 切换模式，写->读
            System.out.print(Charset.forName("utf-8").decode(byteBuffer));  // 这样读取，如果10字节恰好分割了一个字符将出现乱码
            byteBuffer.clear();// 清空,position位置为0，limit=capacity
            //  继续往buffer中写
            bytesRead  = fc.read(byteBuffer);
        }
    }

    private static ByteBuffer reAllocate(ByteBuffer stringBuffer) {
        final int capacity = stringBuffer.capacity();
        byte[] newBuffer = new byte[capacity * 2];
        System.arraycopy(stringBuffer.array(), 0, newBuffer, 0, capacity);
        return (ByteBuffer) ByteBuffer.wrap(newBuffer).position(capacity);
    }
}
