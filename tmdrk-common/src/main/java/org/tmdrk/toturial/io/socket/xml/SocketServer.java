package org.tmdrk.toturial.io.socket.xml;

import org.apache.log4j.Logger;
import org.tmdrk.toturial.io.socket.ServerSocketTest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName SocketServer
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/11/17 23:36
 * @Version 1.0
 **/
public class SocketServer {
    private static Logger logger = Logger.getLogger(ServerSocketTest.class);
    public static void main(String[] args) throws IOException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Message>\n" +
                "<Head>\n" +
                "<_RejCode>000000</_RejCode>\n" +
                "<RouterJnlNo>56165135135</RouterJnlNo>\n" +
                "</Head>\n" +
                "<Body>\n" +
                "<OpenAccountDate>20200202</OpenAccountDate>\n" +
                "<DeptName>蕲春中银富登村镇银行有限公司</DeptName>\n" +
                "<IsStaff>Y</IsStaff>\n" +
                "<ManagerId>E5215554</ManagerId>\n" +
                "</Body>\n" +
                "</Message>";
        /**
         * 基于TCP协议的Socket通信，实现用户登录，服务端
         */
        //1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        ServerSocket serverSocket = new ServerSocket(10088);//1024-65535的某个端口
        //2、调用accept()方法开始监听，等待客户端的连接
        logger.info("server:"+"127.0.0.1"+" port:" + "10088");
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
        os.write(xml.getBytes("UTF-8"));
        logger.info("server:初始化输出流完成");
//        os.write("欢迎您！".getBytes());
        os.flush();

        //5、关闭资源
        os.close();
        br.close();
        isr.close();
        socket.close();
        serverSocket.close();
        logger.info("server:"+"127.0.0.1"+" port:" + "10086 服务关闭成功");
    }
}
