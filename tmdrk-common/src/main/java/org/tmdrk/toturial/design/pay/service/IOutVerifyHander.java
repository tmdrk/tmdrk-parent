package org.tmdrk.toturial.design.pay.service;

import org.tmdrk.toturial.design.pay.dto.OutOrderDto;

/**
 * TODO
 *
 * @author Jie.Zhou
 * @date 2020/8/17 10:02
 */
public interface IOutVerifyHander<I extends OutOrderDto,O> {
    O orderVerify(I outOrderDto);
    O RefundVerify(I outOrderDto);
}
