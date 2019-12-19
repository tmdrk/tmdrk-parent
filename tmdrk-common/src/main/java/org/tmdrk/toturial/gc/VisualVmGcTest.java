package org.tmdrk.toturial.gc;

import java.util.ArrayList;

/**
 * @ClassName VisualVmGcTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/9/9 15:51
 * @Version 1.0
 **/
public class VisualVmGcTest {
    byte[] bytes = new byte[1024*100];

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Object> list = new ArrayList<>();
        while (true){
            list.add(new VisualVmGcTest());
            Thread.sleep(20);
            System.out.println("success "+System.currentTimeMillis());
        }
    }
}
