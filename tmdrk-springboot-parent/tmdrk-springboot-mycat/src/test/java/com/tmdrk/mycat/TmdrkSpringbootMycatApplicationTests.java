package com.tmdrk.mycat;

import com.tmdrk.mycat.entity.Order;
import com.tmdrk.mycat.service.OrderService;
import com.tmdrk.mycat.util.IdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TmdrkSpringbootMycatApplicationTests {
    @Autowired
    OrderService orderService;
    @Test
    public void contextLoads() throws InterruptedException {
        IdWorker idWorker=new IdWorker(13,13); //机器id 与序列id ，也可以不传
        List<Long> orderIds = new ArrayList<>();
        System.out.println("----------------------------------------------------------");
        for(int i=0;i<1000;i++){
            long orderId = idWorker.nextId();
            System.out.println(orderId+","+orderId%4);
            orderIds.add(orderId);
            Thread.sleep(111);
        }
        System.out.println("----------------------------------------------------------");
        for(Long orderId:orderIds){
            Long time = System.currentTimeMillis();
            time = time / 1000;
            Order order = new Order(orderId,0,(byte)1,new BigDecimal("200.23"),time.intValue());
            System.out.println("插入 result="+orderService.insert(order)+"orderId="+orderId+" orderId%4="+orderId%4);
        }

    }

}
