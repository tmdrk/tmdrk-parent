package com.tmdrk.myboot.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 砍价赠品表(BargainGift)实体类
 *
 * @author zhoujie
 * @since 2020-11-17 13:41:49
 */
@Data
@Accessors(chain = true)
public class BargainGift{

    /**
     * id
     */
    private Long id;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 活动配置id
     */
    private Long activityConfigId;

    /**
     * 赠品编号
     */
    @NotNull(message = "赠品编号不能为空")
    private String giftNo;

    /**
     * 赠品名称
     */
    private String giftName;

    /**
     * 赠品类型 coupon:优惠券 wxdeduct:微信立减金
     */
    private String giftType;

    /**
     * 赠品金额
     */
    private Integer giftAmt;

    /**
     * 赠品库存
     */
    @NotNull(message = "赠品库存不能为空")
    private Integer giftStore;

    /**
     * 已使用库存
     */
    private Integer useStore;

    /**
     * 有效截止时间
     */
    private Date effectiveEnd;

    /**
     * 中奖概率
     */
    @NotNull(message = "中奖概率不能为空")
    private BigDecimal probability;

    /**
     * 助力类型 0:首次助力 1:普通助力
     */
    @NotNull(message = "助力类型不能为空")
    private Integer helpType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 数据状态是否删除(0否1是)
     */
    private Boolean del;

}