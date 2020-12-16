package com.tmdrk.myboot.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * BargainActivityConfigDTO
 *
 * @author Jie.Zhou
 * @date 2020/11/17 14:33
 */
@Data
@Accessors(chain = true)
public class BargainActivityConfigDTO {
    @NotEmpty(message = "活动配置id不能为空")
    private Long id;
    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 用户类型 0:新会员 1:新客户 2:老客户
     */
    private Integer userType;

    /**
     * 帮砍获赠类型 nothing:无 wxdeduct:微信立减金
     */
    private String helpBargainGiftType;

    /**
     * 活动帮砍总次数
     */
    @NotEmpty(message = "活动帮砍总次数不能为空")
    private Integer activityHelpCntLimit;

    /**
     * 个人最多获奖次数
     */
    @NotEmpty(message = "个人最多获奖次数不能为空")
    private Integer perPrizeCntLimit;

    /**
     * 个人最高获奖金额
     */
    @NotEmpty(message = "个人最高获奖金额不能为空")
    private Integer perPrizeAmtLimit;

    /**
     * 首次获赠金额
     */
    private Integer firstAmt;

    /**
     * 获赠最大金额
     */
    @NotEmpty(message = "获赠最大金额不能为空")
    private Integer maxAmt;

    /**
     * 获赠最小金额
     */
    @NotEmpty(message = "获赠最小金额不能为空")
    private Integer minAmt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 数据状态是否删除(0否1是)
     */
    private Boolean del;

    /**
     * 赠品列表
     */
    @Valid
    private List<BargainGift> giftList;
}
