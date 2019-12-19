package org.tmdrk.toturial.io.nio.niochat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName NIOClient
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/15 0:31
 * @Version 1.0
 **/
public class NIOClient {
    public static void main(String[] args) {
        try{
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("localhost",8899));

            while(true){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for(SelectionKey selectionKey:selectionKeys){
                    if(selectionKey.isConnectable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        if(client.isConnectionPending()){
                            client.finishConnect();
                            ByteBuffer writerBuffer = ByteBuffer.allocate(1024);
                            writerBuffer.put((LocalDateTime.now() +"成功连接").getBytes());
                            writerBuffer.flip();
                            client.write(writerBuffer);
                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(()->{
                                while(true){
                                    try{
                                        writerBuffer.clear();
                                        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                        String sendMsg = bufferedReader.readLine();
                                        writerBuffer.put(sendMsg.getBytes());
                                        writerBuffer.flip();
                                        client.write(writerBuffer);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        client.register(selector,SelectionKey.OP_READ);
                    }else if(selectionKey.isReadable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int read = client.read(readBuffer);
                        if(read > 0){
                            String receivedMsg = new String(readBuffer.array(), 0, read);
                            System.out.println("receivedMsg:"+receivedMsg);
                        }
                    }
                }
                selectionKeys.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
