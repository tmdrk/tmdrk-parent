package org.tmdrk.toturial.common.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @ClassName TelnetUtil
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/14 0:09
 * @Version 1.0
 **/
public class TelnetUtil {
    private static final int PORT = 80; //端口
    private static final int TIMEOUT = 200; //ms

    public static boolean telnet(String hostname){
        return telnet(hostname,PORT,TIMEOUT);
    }
    public static boolean telnet(String hostname, int port){
        return telnet(hostname,port,TIMEOUT);
    }
    /**
     * @Author zhoujie
     * @Description 测试telnet 机器端口的连通性
     * @Date 0:09 2020/5/14
     * @Param [hostname, port, timeout]
     * @return boolean
     **/
    public static boolean telnet(String hostname, int port, int timeout){
        Socket socket = new Socket();
        boolean isConnected = false;
        try {
            socket.connect(new InetSocketAddress(hostname, port), timeout); // 建立连接
            isConnected = socket.isConnected(); // 通过现有方法查看连通状态
//            System.out.println(isConnected);    // true为连通
        } catch (IOException e) {
            System.out.println("false");        // 当连不通时，直接抛异常，异常捕获即可
        }finally{
            try {
                socket.close();   // 关闭连接
            } catch (IOException e) {
                System.out.println("false");
            }
        }
        return isConnected;
    }

    public static void main(String[] args) {
        // hostname 可以是主机的 IP 或者 域名
        String hostname = "192.168.1.22";
//        String hostname = "www.baidu.com";
        int port = 8088;
        int timeout = 200;
        long duration = System.currentTimeMillis();
        boolean isConnected = telnet(hostname, port, timeout);
        duration = System.currentTimeMillis()-duration;
        System.out.println("telnet "+ hostname + " " + port + "\n==>isConnected: " + isConnected+",duration:"+duration);
    }
}
