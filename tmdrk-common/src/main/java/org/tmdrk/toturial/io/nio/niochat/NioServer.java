package org.tmdrk.toturial.io.nio.niochat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName NioServer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/14 18:49
 * @Version 1.0
 **/
public class NioServer {
    private static Map<String,SocketChannel> clientMap = new HashMap();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8899);
        serverSocket.bind(inetSocketAddress);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            try{
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(selectionKey -> {
                    final SocketChannel client;
                    try{
                        if(selectionKey.isAcceptable()){
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector,SelectionKey.OP_READ);
                            String key = "【"+ UUID.randomUUID().toString() +"】";
                            clientMap.put(key,client);

                        }else if(selectionKey.isReadable()){
                            client = (SocketChannel) selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            int read = client.read(readBuffer);
                            if(read > 0 ){
                                readBuffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                String receiveMsg = String.valueOf(charset.decode(readBuffer).array());
                                System.out.println(client+":"+receiveMsg);

                                String sendKey = null;
                                for(Map.Entry<String,SocketChannel> entry:clientMap.entrySet()){
                                    if(client == entry.getValue()){
                                        sendKey = entry.getKey();
                                        break;
                                    }
                                }
                                for(Map.Entry<String,SocketChannel> entry:clientMap.entrySet()){
                                    SocketChannel socketChannel = entry.getValue();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put((sendKey+":"+receiveMsg).getBytes());
                                    writeBuffer.flip();
                                    socketChannel.write(writeBuffer);
                                }
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
