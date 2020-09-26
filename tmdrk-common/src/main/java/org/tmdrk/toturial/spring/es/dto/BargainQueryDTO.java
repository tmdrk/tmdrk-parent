package org.tmdrk.toturial.spring.es.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * BargainQueryDTO
 *
 * @author Jie.Zhou
 * @date 2020/9/24 10:04
 */
@Data
@Accessors(chain = true)
public class BargainQueryDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 关联活动id
     */
    private Long activityId;

    /**
     * 用户ID
     */
    private String customerId;

    /**
     * 砍价状态 0:砍价中、1:待领取、2待支付、3:已领取、4:砍价失败
     */
    private Integer status;

    /**
     * 关联商品skuCode
     */
    private String skuCode;

    private String asc;

    private String desc;

}
