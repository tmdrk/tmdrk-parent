package org.tmdrk.toturial.design.pay.service;

import org.tmdrk.toturial.design.pay.dto.OutRefundDto;

/**
 * TODO
 *
 * @author Jie.Zhou
 * @date 2020/8/17 9:45
 */
public interface IOutRefundHandler<I extends OutRefundDto,O> {
    default O submitRefund(I outOrderDto){
        checkRefund(outOrderDto);
        postProcessBeforeRefund(outOrderDto);
        doSaveRefund(outOrderDto);
        O result = doSubmitRefund(outOrderDto);
        postProcessAfterRefund(outOrderDto,result);
        return result;
    }
    void checkRefund(I outOrderDto);
    void postProcessBeforeRefund(I outOrderDto);
    void doSaveRefund(I outOrderDto);
    <T> T doSubmitRefund(I outOrderDto);
    void postProcessAfterRefund(I outOrderDto,O result);
}
