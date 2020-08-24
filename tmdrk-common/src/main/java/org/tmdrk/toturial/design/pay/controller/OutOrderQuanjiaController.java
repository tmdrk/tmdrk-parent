package org.tmdrk.toturial.design.pay.controller;

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
 * OutOrderQuanjiaController
 *
 * @author Jie.Zhou
 * @date 2020/8/17 9:15
 */
@RestController
public class OutOrderQuanjiaController {

    @RequestMapping("/quanjia")
    public Result quanjiaOrder(OutOrderDto outOrderDto){
        IOutOrderHandler<OutOrderDto, OutOrderQuanjiaDto> outOrderQuanjiaHandler = new OutOrderQuanjiaHandler();
        OutOrderQuanjiaDto outOrderQuanjiaDto = outOrderQuanjiaHandler.submitOrder(outOrderDto);
        return Result.success(outOrderQuanjiaDto);
    }

    @GetMapping("/quanjia")
    public Result quanjiaVerify(OutOrderDto outOrderDto){
        IOutVerifyHander<OutOrderDto, OutOrderQuanjiaDto> outVerifyHander = new OutOrderQuanjiaHandler();
        OutOrderQuanjiaDto outOrderQuanjiaDto = outVerifyHander.orderVerify(outOrderDto);
        return Result.success(outOrderQuanjiaDto);
    }

    @PostMapping("/quanjia/refund")
    public Result quanjiaRefund(OutRefundDto outRefundDto){
        IOutRefundHandler<OutRefundDto, OutRefundQuanjiaDto> outOrderQuanjiaHandler = new OutOrderQuanjiaHandler();
        OutRefundQuanjiaDto result = outOrderQuanjiaHandler.submitRefund(outRefundDto);
        return Result.success(result);
    }
}
