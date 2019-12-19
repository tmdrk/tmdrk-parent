package org.tmdrk.toturial.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName SelectorDemo
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/14 16:35
 * @Version 1.0
 **/
public class SelectorDemo {
    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];
        for(int i=0;i<5;i++){
            ports[i] = 5000+i;
        }
        Selector selector = Selector.open();
//        System.out.println(SelectorProvider.provider().getClass());
        for(int i = 0 ;i<ports.length;i++){
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(ports[i]);
            serverSocket.bind(inetSocketAddress);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口："+ports[i]);
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while(true){
            int numbers = selector.select();
            System.out.println("numbers:"+numbers);
            while (numbers > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println("selectionKeys:" + selectionKeys);
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                System.out.println("selectionKeys.size:" + selectionKeys.size());
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        iterator.remove();
                        System.out.println("获得客户端连接：" + socketChannel);
                    } else if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        int byteReads = 0;
                        while (true) {
                            byteBuffer.clear();
                            int read = socketChannel.read(byteBuffer);
                            if (read <= 0) {
                                break;
                            }
                            byteBuffer.flip();
                            socketChannel.write(byteBuffer);
                            byteReads += read;
                        }
                        System.out.println("读取：" + byteReads + "，来自于" + socketChannel);
                        iterator.remove();
                    }
                }
            }
        }

    }
}
