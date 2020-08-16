package org.tmdrk.toturial.design.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmdrk.toturial.design.pay.service.IOutOrderHandler;
import org.tmdrk.toturial.design.pay.service.IOutOrderService;

/**
 * @ClassName OutOrderElemeHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/8/16 23:55
 * @Version 1.0
 **/
@Service("outOrderElemeHandler")
public class OutOrderElemeHandler implements IOutOrderHandler {
    @Autowired
    private IOutOrderService IOutOrderService;

    @Override
    public void checkOrder() {
        System.out.println("饿了么校验订单完成");
    }

    @Override
    public void postProcessBeforeOrder() {
        System.out.println("饿了么下单前置处理完成");
    }

    @Override
    public void doSaveOrder() {
        System.out.println("饿了么落单处理完成");
    }

    @Override
    public String doSubmitOrder() {
        System.out.println("饿了么工行处理完成");
        return "欢迎来到工行支付页";
    }

    @Override
    public void postProcessAfterOrder() {
        System.out.println("饿了么下单后置处理完成");
    }
}
