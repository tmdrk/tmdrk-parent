package org.tmdrk.toturial.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName PingUtil
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/13 23:46
 * @Version 1.0
 **/
public class PingUtil {
    static Logger logger = LoggerFactory.getLogger(PingUtil.class);

    public static boolean ping(String ipAddress) throws Exception {
        int timeOut = 3000 ;
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);
        return status;
    }

    public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut;
        // Linux命令如下
        // String pingCommand = "ping" -c " + pingTimes + " -w " + timeOut + ipAddress;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(pingCommand);
            }
            // 执行命令并获取输出
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int connectedCount = 0;
            String line;
            // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }
            // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return connectedCount == pingTimes;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
    private static int getCheckResult(String line) {  // System.out.println("控制台输出的结果为:"+line);
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",    Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();
            String url = "192.168.1.21";
//            System.out.println("ping:"+ping(url));
            System.out.println("ping:"+ping(url,3,2000));
            System.out.println("ping:"+ping(url,3,2000));
            System.out.println("ping:"+ping(url,3,2000));
            System.out.println("ping:"+ping(url,3,2000));
            System.out.println("ping:"+ping(url,3,2000));
            System.out.println("ping:"+ping(url,3,2000));
            System.out.println("ping:"+ping(url,3,2000));
            System.out.println("ping:"+ping(url,3,2000));
            System.out.println("ping:"+ping(url,3,2000));
            System.out.println("ping:"+ping(url,3,2000));
            start = System.currentTimeMillis()-start;
            System.out.println("duration:"+start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
