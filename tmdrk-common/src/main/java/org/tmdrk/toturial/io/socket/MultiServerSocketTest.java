package org.tmdrk.toturial.io.socket;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName MultiServerSocketTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/4/23 19:07
 * @Version 1.0
 **/
public class MultiServerSocketTest extends Thread{
    private static Logger logger = Logger.getLogger(MultiServerSocketTest.class);

    Socket socket =null;
    public MultiServerSocketTest(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String info =null;
            logger.info("线程名:"+Thread.currentThread().getName()+" server:初始化输入流完成");
            while((info=br.readLine())!=null){
                logger.info("线程名:"+Thread.currentThread().getName()+" 我是服务器，客户端说：" + info);
            }
            socket.shutdownInput();//关闭输入流
            //4、获取输出流，响应客户端的请求
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            logger.info("线程名:"+Thread.currentThread().getName()+" server:初始化输出流完成");
            pw.write("欢迎您！");
            pw.flush();

            //5、关闭资源
            os.close();
            br.close();
            isr.close();
            socket.close();
//            serverSocket.close();
            logger.info("线程名:"+Thread.currentThread().getName()+" server:"+"127.0.0.1"+" port:" + "10086 服务关闭成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        //服务器代码
        ServerSocket serverSocket = new ServerSocket(10086);
        Socket socket = null;
        int count = 0;//记录客户端的数量
        while (true) {
            socket = serverSocket.accept();
            MultiServerSocketTest serverThread = new MultiServerSocketTest(socket);
            serverThread.start();
            count++;
            System.out.println("客户端连接的数量：" + count);
        }
    }
}
