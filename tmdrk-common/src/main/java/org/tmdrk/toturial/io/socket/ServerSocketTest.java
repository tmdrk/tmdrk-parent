package org.tmdrk.toturial.io.socket;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName ServerSocketTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/4/23 16:29
 * @Version 1.0
 **/
public class ServerSocketTest {
    private static Logger logger = Logger.getLogger(ServerSocketTest.class);
    public static void main(String[] args) throws Exception {
        /**
         * 基于TCP协议的Socket通信，实现用户登录，服务端
         */
        //1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        ServerSocket serverSocket =new ServerSocket(10086);//1024-65535的某个端口
        //2、调用accept()方法开始监听，等待客户端的连接
        logger.info("server:"+"127.0.0.1"+" port:" + "10086");
        Socket socket = serverSocket.accept();
        logger.info("server:收到请求");
        //3、获取输入流，并读取客户端信息
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        String info =null;
        logger.info("server:初始化输入流完成");
        while((info=br.readLine())!=null){
            logger.info("我是服务器，客户端说：" + info);
        }
        socket.shutdownInput();//关闭输入流
        //4、获取输出流，响应客户端的请求
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        logger.info("server:初始化输出流完成");
        pw.write("欢迎您！");
        pw.flush();

        //5、关闭资源
        os.close();
        br.close();
        isr.close();
        socket.close();
        serverSocket.close();
        logger.info("server:"+"127.0.0.1"+" port:" + "10086 服务关闭成功");
    }
}
