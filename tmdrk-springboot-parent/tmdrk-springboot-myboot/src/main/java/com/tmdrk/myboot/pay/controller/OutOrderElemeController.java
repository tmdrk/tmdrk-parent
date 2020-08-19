package com.tmdrk.myboot.pay.controller;

import com.tmdrk.myboot.pay.dto.*;
import com.tmdrk.myboot.pay.service.IOutOrderHandler;
import com.tmdrk.myboot.pay.service.IOutRefundHandler;
import com.tmdrk.myboot.pay.service.IOutVerifyHander;
import com.tmdrk.myboot.pay.service.impl.OutOrderElemeHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OutOrderElemeController
 * @Description 饿了么下单控制器
 * @Author zhoujie
 * @Date 2020/8/16 23:31
 * @Version 1.0
 **/
@RestController
public class OutOrderElemeController {

    @PostMapping("/eleme")
    public Result elemeOrder(OutOrderElemeAssistDto outOrderDto){
        System.out.println("饿了么下单开始");
        IOutOrderHandler<OutOrderElemeAssistDto,String> outOrderElemeHandler = new OutOrderElemeHandler();
        String form = outOrderElemeHandler.submitOrder(outOrderDto);
        return Result.success(form);
    }

    @GetMapping("/eleme")
    public Result elemeVerify(OutOrderDto outOrderDto){
        System.out.println("饿了么订单查询开始");
        IOutVerifyHander<OutOrderDto, OutOrderElemeDto> outOrderElemeHandler = new OutOrderElemeHandler();
        OutOrderElemeDto dto = outOrderElemeHandler.orderVerify(outOrderDto);
        return Result.success(dto);
    }

    @PostMapping("/eleme/refund")
    public Result elemeRefund(OutRefundDto outRefundDto){
        System.out.println("饿了么退款开始");
        IOutRefundHandler<OutRefundDto, OutRefundElemeDto> outOrderHandler = new OutOrderElemeHandler();
        OutRefundElemeDto form = outOrderHandler.submitRefund(outRefundDto);
        return Result.success(form);
    }
}
