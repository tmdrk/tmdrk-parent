package org.tmdrk.toturial.design.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmdrk.toturial.design.pay.dto.*;
import org.tmdrk.toturial.design.pay.service.IOutOrderHandler;
import org.tmdrk.toturial.design.pay.service.IOutOrderService;
import org.tmdrk.toturial.design.pay.service.IOutRefundHandler;
import org.tmdrk.toturial.design.pay.service.IOutVerifyHander;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @ClassName OutOrderElemeHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/8/16 23:55
 * @Version 1.0
 **/
@Service("outOrderElemeHandler")
public class OutOrderElemeHandler implements IOutOrderHandler<OutOrderElemeAssistDto,String> , IOutRefundHandler<OutRefundDto,OutRefundElemeDto>, IOutVerifyHander {

    @Autowired
    private IOutOrderService IOutOrderService;

    //下单 order

    @Override
    public void checkOrder(OutOrderElemeAssistDto outOrderDto) {
        System.out.println("饿了么校验订单完成");
    }

    @Override
    public void postProcessBeforeOrder(OutOrderElemeAssistDto outOrderDto) {
        System.out.println("饿了么下单前置处理完成");
    }

    @Override
    public void doSaveOrder(OutOrderElemeAssistDto outOrderDto) {
        System.out.println("饿了么落单");
    }

    @Override
    public String doSubmitOrder(OutOrderElemeAssistDto outOrderDto) {
        System.out.println("饿了么工行处理完成");
        return "欢迎来到工行支付页";
    }

    @Override
    public void postProcessAfterOrder(OutOrderElemeAssistDto outOrderDto) {
        System.out.println("饿了么下单后置处理完成");
    }

    //退款 refund

    @Override
    public void checkRefund(OutRefundDto outOrderDto) {
        System.out.println("饿了么校验退款完成");
    }

    @Override
    public void postProcessBeforeRefund(OutRefundDto outOrderDto) {
        System.out.println("饿了么退款前置处理完成");
    }

    @Override
    public void doSaveRefund(OutRefundDto outOrderDto) {
        System.out.println("饿了么退款落单");
    }

    @Override
    public OutRefundElemeDto doSubmitRefund(OutRefundDto outOrderDto) {
        System.out.println("饿了么工行处理完成");
        return new OutRefundElemeDto();
    }

    @Override
    public void postProcessAfterRefund(OutRefundDto outOrderDto,OutRefundElemeDto result) {
        System.out.println("饿了么下单后置处理完成");
    }

    //查证

    @Override
    public OutOrderElemeDto orderVerify(OutOrderDto outOrderDto) {
        System.out.println("饿了么订单查询");
        return new OutOrderElemeDto();
    }

    @Override
    public OutRefundElemeDto RefundVerify(OutOrderDto outOrderDto) {
        System.out.println("饿了么退款查询");
        return new OutRefundElemeDto();
    }

    public static void main(String[] args) {
        Type genericSuperclass = OutOrderElemeHandler.class.getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        for (Type type:actualTypeArguments) {
            System.out.println(type);
        }
    }
}
