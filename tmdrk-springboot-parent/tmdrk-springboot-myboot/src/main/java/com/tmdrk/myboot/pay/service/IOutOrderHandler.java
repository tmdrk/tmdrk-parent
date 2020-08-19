package com.tmdrk.myboot.pay.service;

import com.tmdrk.myboot.pay.dto.OutOrderDto;

/**
 * @ClassName IOutOrderHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/8/16 23:52
 * @Version 1.0
 **/
public interface IOutOrderHandler<I extends OutOrderDto,O> {
    default O submitOrder(I outOrderDto){
        checkOrder(outOrderDto);
        postProcessBeforeOrder(outOrderDto);
        doSaveOrder(outOrderDto);
        O result = doSubmitOrder(outOrderDto);
        postProcessAfterOrder(outOrderDto);
        return result;
    }
    void checkOrder(I outOrderDto);
    void postProcessBeforeOrder(I outOrderDto);
    void doSaveOrder(I outOrderDto);
    <T> T doSubmitOrder(I outOrderDto);
    void postProcessAfterOrder(I outOrderDto);


}
