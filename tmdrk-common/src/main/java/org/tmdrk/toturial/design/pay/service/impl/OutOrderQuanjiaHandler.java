package org.tmdrk.toturial.design.pay.service.impl;

import org.springframework.stereotype.Service;
import org.tmdrk.toturial.design.pay.dto.QuanjiaOrderDto;
import org.tmdrk.toturial.design.pay.service.IOutOrderHandler;

/**
 * @ClassName OutOrderQuanjiaHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/8/16 23:55
 * @Version 1.0
 **/
@Service("outOrderElemeHandler")
public class OutOrderQuanjiaHandler implements IOutOrderHandler {
    @Override
    public void checkOrder() {
        System.out.println("全家校验订单完成");
    }

    @Override
    public void postProcessBeforeOrder() {
        System.out.println("全家下单前置处理完成");
    }


    @Override
    public void doSaveOrder() {
        System.out.println("全家落单处理完成");
    }

    @Override
    public QuanjiaOrderDto doSubmitOrder() {
        System.out.println("工行处理完成");
        return new QuanjiaOrderDto();
    }

    @Override
    public void postProcessAfterOrder() {
        System.out.println("全家下单后置处理完成");
    }
}
