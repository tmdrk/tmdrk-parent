package org.tmdrk.toturial.io.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @ClassName EncodeAndDecodeDemo
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/15 1:23
 * @Version 1.0
 **/
public class EncodeAndDecodeDemo {
    public static void main(String[] args) throws IOException {
        String inputFile = "inputFile.txt";
        String outputFile = "outputFile.txt";
        RandomAccessFile inputRandomAccessFile = new RandomAccessFile("F:/dir1/"+inputFile, "r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile("F:/dir1/"+outputFile, "rw");
        long inputLength = new File("F:/dir1/"+inputFile).length();
        FileChannel inputChannel = inputRandomAccessFile.getChannel();
        FileChannel outputChannel = outputRandomAccessFile.getChannel();
        MappedByteBuffer inputData = inputChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputLength);

        System.out.println("==================");
        Charset.availableCharsets().forEach((k,v) -> {
            System.out.println(k+","+v);
        });
        System.out.println("==================");

        Charset charset = Charset.forName("utf-8");
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharsetEncoder charsetEncoder = charset.newEncoder();
        CharBuffer charBuffer = charsetDecoder.decode(inputData);
        ByteBuffer outputData = charsetEncoder.encode(charBuffer);
        System.out.println(new String(outputData.array(),0,outputData.array().length));
        outputChannel.write(outputData);

        inputRandomAccessFile.close();
        outputRandomAccessFile.close();
    }
}
