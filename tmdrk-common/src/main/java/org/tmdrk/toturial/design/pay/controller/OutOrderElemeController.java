package org.tmdrk.toturial.design.pay.controller;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tmdrk.toturial.design.pay.dto.*;
import org.tmdrk.toturial.design.pay.service.IOutOrderHandler;
import org.tmdrk.toturial.design.pay.service.IOutRefundHandler;
import org.tmdrk.toturial.design.pay.service.IOutVerifyHander;
import org.tmdrk.toturial.design.pay.service.impl.OutOrderElemeHandler;
import org.tmdrk.toturial.design.pay.service.impl.OutOrderQuanjiaHandler;
import org.tmdrk.toturial.spring.es.Result;

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
    public Result elemeOrder(OutOrderElemeDto outOrderElemeDto){
        OutOrderElemeAssistDto outOrderElemeAssistDto = new OutOrderElemeAssistDto();
        BeanUtil.copyProperties(outOrderElemeDto,outOrderElemeAssistDto);
        IOutOrderHandler<OutOrderElemeAssistDto,String> outOrderElemeHandler = new OutOrderElemeHandler();
        String form = outOrderElemeHandler.submitOrder(outOrderElemeAssistDto);
        return Result.success(form);
    }

    @GetMapping("/eleme")
    public Result elemeVerify(OutOrderDto outOrderDto){

        IOutVerifyHander<OutOrderDto,OutOrderElemeDto> outOrderElemeHandler = new OutOrderElemeHandler();
        OutOrderElemeDto dto = outOrderElemeHandler.orderVerify(outOrderDto);
        return Result.success(dto);
    }

    @PostMapping("/eleme/refund")
    public Result elemeRefund(OutRefundDto outRefundDto){
        IOutRefundHandler<OutRefundDto, OutRefundElemeDto> outOrderHandler = new OutOrderElemeHandler();
        OutRefundElemeDto form = outOrderHandler.submitRefund(outRefundDto);
        return Result.success(form);
    }
}
