package org.tmdrk.toturial.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName AQSTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/4/25 11:18
 * @Version 1.0
 **/
public class AQSTest2 {
    public static void main(String[] args) {
        MyTest myTest = new MyTest();
        Thread2 t2 = new Thread2(myTest);
        t2.start();
    }
}

