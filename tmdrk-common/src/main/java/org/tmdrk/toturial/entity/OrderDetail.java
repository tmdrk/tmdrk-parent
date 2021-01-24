package org.tmdrk.toturial.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * OrderDetail
 *
 * @author Jie.Zhou
 * @date 2021/1/7 13:08
 */
@Data
@Accessors(chain = true)
public class OrderDetail {
    private Long id;
    private String outTradeNo;
    private Integer price;
    private String remark;
}
