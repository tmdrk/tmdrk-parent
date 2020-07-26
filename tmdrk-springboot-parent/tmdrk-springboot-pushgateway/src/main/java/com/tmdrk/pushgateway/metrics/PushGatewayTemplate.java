package com.tmdrk.pushgateway.metrics;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.PushGateway;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

/**
 * @ClassName PushGatewayUtil
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/24 2:29
 * @Version 1.0
 **/
@Component
public class PushGatewayTemplate {

    PushGateway prometheusPush = new PushGateway("192.168.1.15:9091");
    Counter counter = Counter.build()
            .subsystem("subsystem")
            .namespace("namespace")
            .name("pv_name")
            .labelNames("url","type","instance")
            .help("Counter 实例")
            .register();

    public void pushCounter(int count){
        try {
            counter.labels("/hello","GET","192.168.1.15:9091").inc(count);
            prometheusPush.push(counter,"pushgateway");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("开始...");
        PushGatewayTemplate pushGatewayTemplate = new PushGatewayTemplate();

        for(double i=0;i<5000;i++) {
            Double abs = Math.abs(Math.cos(i/10)*1000);
            int count = abs.intValue();
            System.out.println("第"+(i+1)+"次发送 count="+count);
            pushGatewayTemplate.pushCounter(count);
            Thread.sleep(2000);
        }
        System.out.println("结束");
    }
}
