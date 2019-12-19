package org.tmdrk.toturial.io.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @ClassName IntBufferDemo
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/14 9:31
 * @Version 1.0
 **/
public class IntBufferDemo {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println(buffer.capacity()+","+buffer.limit()+","+buffer.position());
        for(int i=0;i<buffer.capacity()-2;i++){
            buffer.put(new SecureRandom().nextInt(20));
            System.out.println(buffer.capacity()+","+buffer.limit()+","+buffer.position());
        }
        buffer.flip();
        System.out.println("flip:"+buffer.capacity()+","+buffer.limit()+","+buffer.position());
        buffer.position(6);
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
            System.out.println(buffer.capacity()+","+buffer.limit()+","+buffer.position());
        }
    }
}
