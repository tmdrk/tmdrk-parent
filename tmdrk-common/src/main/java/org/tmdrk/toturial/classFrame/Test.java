package org.tmdrk.toturial.classFrame;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @ClassName Test
 * @Description 测试jdk动态代理，生成动态代理类
 * @Author zhoujie
 * @Date 2020/6/11 15:03
 * @Version 1.0
 **/
public class Test {

    public static void main(String[] args) throws IOException {
        transClass();
    }
    public static void transClass() throws IOException {
        URL resource = Test.class.getClass().getResource("/");
        byte[] bts = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{HelloService.class});
        File file = new File(resource.getPath(),"$Proxy0.class");
        if (!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bts);
        fos.flush();
        fos.close();
        System.out.println(resource.getPath());
    }
}
