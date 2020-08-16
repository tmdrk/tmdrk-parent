package org.tmdrk.toturial.design.pay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tmdrk.toturial.design.pay.dto.QuanjiaOrderDto;
import org.tmdrk.toturial.design.pay.service.IOutOrderHandler;
import org.tmdrk.toturial.design.pay.service.impl.OutOrderElemeHandler;
import org.tmdrk.toturial.design.pay.service.impl.OutOrderQuanjiaHandler;
import org.tmdrk.toturial.spring.es.Result;



/**
 * @ClassName OutOrderController
 * @Description 三方下单控制器
 * @Author zhoujie
 * @Date 2020/8/16 23:31
 * @Version 1.0
 **/
@RestController
@RequestMapping("/out-order")
public class OutOrderController {

    @RequestMapping("/eleme")
    public Result ElemeOrder(){
        IOutOrderHandler<String> outOrderElemeHandler = new OutOrderElemeHandler();
        String form = outOrderElemeHandler.submitOrder();
        return Result.success(form);
    }

    @RequestMapping("/quanjia")
    public Result quanjiaOrder(){
        IOutOrderHandler<QuanjiaOrderDto> outOrderQuanjiaHandler = new OutOrderQuanjiaHandler();
        QuanjiaOrderDto quanjiaOrderDto = outOrderQuanjiaHandler.submitOrder();
        return Result.success(quanjiaOrderDto);
    }
}
