package com.tmdrk.myboot.pay.service;

import com.tmdrk.myboot.pay.dto.OutOrderDto;

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
