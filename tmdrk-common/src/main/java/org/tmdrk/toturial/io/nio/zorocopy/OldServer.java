package org.tmdrk.toturial.io.nio.zorocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName OldServer
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/16 3:14
 * @Version 1.0
 **/
public class OldServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8899);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            try{

            }catch (Exception e){

            }
        }
    }
}
