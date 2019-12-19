package org.tmdrk.toturial.io;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @ClassName RemoteReadOrWriteFile
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/8 16:02
 * @Version 1.0
 **/
public class RemoteReadOrWriteFile {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://192.168.1.74/Test1/upload/1.txt");
        InputStream ism=url.openStream();
        byte[] bytes=new byte[1024];
        ism.read(bytes);
        String str=new String(bytes,"utf-8");
        System.err.println(str);
        while(ism.read(bytes)>-1){
            System.err.println(str);
        }
    }
}
