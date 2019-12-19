package org.tmdrk.toturial.io.socket;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * @ClassName SocketTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/4/23 16:29
 * @Version 1.0
 **/
public class SocketTest {
    private static org.apache.log4j.Logger logger = Logger.getLogger(SocketTest.class);
    public static void main(String[] args) throws Exception {
        //客户端
        //1、创建客户端Socket，指定服务器地址和端口
        Socket socket = new Socket("localhost",10086);
        //2、获取输出流，向服务器端发送信息
        System.out.println("休眠10秒");
        Thread.sleep(10000);
        OutputStream os = socket.getOutputStream();//字节输出流
        PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流
        pw.write("用户名：admin；密码：123");
        pw.flush();
        socket.shutdownOutput();
        logger.info("客户端输出流关闭");
        //3、获取输入流，并读取服务器端的响应信息
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String info = null;
        while((info=br.readLine())!=null){
            logger.info("我是客户端，服务器说："+info);
        }

        //4、关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
        logger.info("客户端关闭");
    }
}
