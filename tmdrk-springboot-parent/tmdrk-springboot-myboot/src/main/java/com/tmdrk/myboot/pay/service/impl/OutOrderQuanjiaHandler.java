package com.tmdrk.myboot.pay.service.impl;

import com.tmdrk.myboot.pay.dto.OutOrderDto;
import com.tmdrk.myboot.pay.dto.OutOrderQuanjiaDto;
import com.tmdrk.myboot.pay.dto.OutRefundDto;
import com.tmdrk.myboot.pay.dto.OutRefundQuanjiaDto;
import com.tmdrk.myboot.pay.service.IOutOrderHandler;
import com.tmdrk.myboot.pay.service.IOutRefundHandler;
import com.tmdrk.myboot.pay.service.IOutVerifyHander;
import org.springframework.stereotype.Service;

/**
 * @ClassName OutOrderQuanjiaHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/8/16 23:55
 * @Version 1.0
 **/
@Service("outOrderQuanjiaHandler")
public class OutOrderQuanjiaHandler implements IOutOrderHandler, IOutRefundHandler<OutRefundDto, OutRefundQuanjiaDto>, IOutVerifyHander {

    @Override
    public void checkOrder(OutOrderDto outOrderDto) {
        System.out.println("纳客宝校验订单完成");
    }

    @Override
    public void postProcessBeforeOrder(OutOrderDto outOrderDto) {
        System.out.println("纳客宝下单前置处理完成");
    }

    @Override
    public void doSaveOrder(OutOrderDto outOrderDto) {
        System.out.println("纳客宝落单");
    }

    @Override
    public OutOrderQuanjiaDto doSubmitOrder(OutOrderDto outOrderDto) {
        System.out.println("工行处理完成");
        return new OutOrderQuanjiaDto();
    }

    @Override
    public void postProcessAfterOrder(OutOrderDto outOrderDto) {
        System.out.println("纳客宝下单后置处理完成");
    }

    //退款

    @Override
    public void checkRefund(OutRefundDto outOrderDto) {
        System.out.println("纳客宝校验退款完成");
    }

    @Override
    public void postProcessBeforeRefund(OutRefundDto outOrderDto) {
        System.out.println("纳客宝退款前置处理完成");
    }

    @Override
    public void doSaveRefund(OutRefundDto outOrderDto) {
        System.out.println("纳客宝退款落单");
    }

    @Override
    public OutRefundQuanjiaDto doSubmitRefund(OutRefundDto outOrderDto) {
        System.out.println("工行处理完成");
        return new OutRefundQuanjiaDto();
    }

    @Override
    public void postProcessAfterRefund(OutRefundDto outOrderDto, OutRefundQuanjiaDto result) {
        System.out.println("纳客宝退款后置处理完成");
    }

    //查证

    @Override
    public OutOrderQuanjiaDto orderVerify(OutOrderDto outOrderDto) {
        System.out.println("纳客宝订单查询");
        return new OutOrderQuanjiaDto();
    }

    @Override
    public OutRefundQuanjiaDto RefundVerify(OutOrderDto outOrderDto) {
        System.out.println("纳客宝退款查询");
        return new OutRefundQuanjiaDto();
    }
}
