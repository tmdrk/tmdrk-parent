package com.tmdrk.pushgateway.metrics;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.PushGateway;

import java.io.IOException;
import java.util.Random;

/**
 * @ClassName PushGatewayUtil
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/24 2:29
 * @Version 1.0
 **/
public class PushGatewayUtil {

    static PushGateway prometheusPush = new PushGateway("192.168.1.15:9091");
    static Counter counter = Counter.build()
            .subsystem("subsystem")
            .namespace("namespace")
            .name("pv_name")
            .labelNames("url","type","instance")
            .help("Counter 实例")
            .register();
    static Counter counter1 = Counter.build()
            .subsystem("subsystem1")
            .namespace("namespace1")
            .name("pv_name_1")
            .labelNames("url","type","instance")
            .help("Counter 实例")
            .register();

    public static void pushCounter(int count){
        try {
            Counter counter = Counter.build()
                    .subsystem("subsystem")
                    .namespace("namespace")
                    .name("pv_name")
                    .labelNames("url","type","instance")
                    .help("Counter 实例")
                    .register();
            counter.labels("/hello","GET","192.168.1.15:9091").inc(count);
            prometheusPush.push(counter,"pushgateway");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pushCounter1(){
        try {
            counter1.labels("/hello","GET","192.168.1.15:9091").inc(1);
            prometheusPush.push(counter,"pushgateway");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("开始...");
        Random random = new Random();
//        for(int i=0;i<300;i++){
//            System.out.println("第"+(i+1)+"次发送");
//            PushGatewayUtil.pushCounter();
//            if((50<i && i<150)||(200<i && i<250)){
//                Thread.sleep(random.nextInt(500));
//            }else{
//                Thread.sleep(random.nextInt(1000));
//            }
//        }
        for(double i=0;i<5000;i++) {
            Double abs = Math.abs(Math.cos(i/10)*1000);
            int count = abs.intValue();
            System.out.println("第"+(i+1)+"次发送 count="+count);
            PushGatewayUtil.pushCounter(count);
            Thread.sleep(2000);
        }
//        PushGatewayUtil.pushCounter1();
        System.out.println("结束");
    }
}
