package com.tmdrk.myboot.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * BargainActivityUpdateDTO
 *
 * @author Jie.Zhou
 * @date 2020/9/9 9:46
 */
@Data
@Accessors(chain = true)
public class BargainActivityUpdateDTO {
    /**
     * id
     */
    @NotEmpty(message = "活动id不能为空")
    private Long id;

    private Long tenantId;

    /**
     * 活动名称
     */
    @NotEmpty(message = "活动名称不能为空")
    @Size(max = 255)
    private String name;

    /**
     * 活动状态(new:未开始,open:进行中,close:已关闭)
     */
    private String status;

    /**
     * 活动开始时间
     */
    @NotEmpty(message = "活动开始时间不能为空")
    private Date startTime;

    /**
     * 活动结束时间
     */
    @NotEmpty(message = "活动结束时间不能为空")
    private Date endTime;

    /**
     * 砍价限定时间单位 SECOND、MINUTE、HOUR、DAY
     */
    private String limitUnit;

    /**
     * 砍价限定时间
     */
    @Min(value = 0,message ="砍价限定时间不合法")
    private Integer limitTime;

//    /**
//     * 兑换期限类型 0：固定日期 1：固定时间
//     */
//    @Min(value = 0,message ="兑换期限类型不合法")
//    private Integer exchangePeriodType;
//
//    /**
//     * 有效天数
//     */
//    private Integer effectiveDays;
//
//    /**
//     * 兑换结束时间
//     */
//    private Date exchangeEndTime;
//
//    /**
//     * 兑换开始时间
//     */
//    private Date exchangeStartTime;

    /**
     * 支付时间单位 SECOND、MINUTE、HOUR、DAY 默认小时
     */
    private String payTimeUnit;

    /**
     * 支付有效时间
     */
    @Min(value = 0,message ="支付有效时间不合法")
    private Integer payEffectiveTime;

//    /**
//     * 砍价类型 0：金额随机，1：金额固定
//     */
//    @Min(value = 0,message ="砍价类型不合法")
//    private Integer bargainType;
//
//    /**
//     * 帮砍人数
//     */
//    @Min(value = 0,message ="帮砍人数不合法")
//    private Integer helpBargainCnt;
//
//    /**
//     * 砍价次数限制
//     */
//    @Min(value = 1,message ="砍价次数不合法")
//    private Integer bargainLimitCnt;

    /**
     * 限购类型 0:不限购 1:限购
     */
    private Integer purchaseLimitType;

    /**
     * 限购数量
     */
    private Integer purchaseLimitCnt;

//    /**
//     * 帮砍获赠类型 nothing:无 coupon:优惠券 points:积分 wxdeduct:微信立减金
//     */
//    @NotEmpty(message = "帮砍获赠类型不能为空", groups = {Group.Update.class})
//    private String helpBargainGiftType;
//
//    /**
//     * 帮砍获赠
//     */
//    private String helpBargainGift;

    /**
     * 参与人数限制 ALL:全部、NEW:新用户、OLD:老用户、VIP:会员
     */
//    @NotEmpty(message = "参与人数限制不能为空", groups = {Group.Update.class})
    private String participantsLimitType;

    /**
     * 帮砍赠品领取次数限制，0:不限制
     */
//    @NotEmpty(message = "帮砍赠品领取次数限制不能为空", groups = {Group.Save.class})
//    @Min(value = 0,message ="帮砍赠品领取次数限制不合法")
//    private Integer helpBargainGiftLimit;

    /**
     * 发起人数
     */
    private Integer initiatorCnt;

    /**
     * 帮砍总数
     */
    private Integer helpBargainTotalCnt;

    /**
     * 砍成人数
     */
    private Integer bargainSuccessCnt;

    /**
     * 成单需要新用户开卡 0:不需要 1:需要
     */
    @NotEmpty(message = "成单需要新用户开卡不能为空")
    private Boolean needNewCard;

    /**
     * 成交规则 0:没有砍到底价无法成交 1:没有砍到底价可以成交
     */
    @NotEmpty(message = "成交规则不能为空")
    private Integer dealRule;

    /**
     * 关联活动编码
     */
    @NotEmpty(message = "关联活动编码不能为空")
    private String associatedActivityCode;

    /**
     * 砍价奖金周期单位
     */
    private String bargainBonusCycleUnit;

    /**
     * 砍价奖金周期次数
     */
    private Integer bargainBonusCycleCnt;

    /**
     * 砍价奖金周期上限  0:不限制
     */
    @NotEmpty(message = "活动奖金周期上限不能为空")
    private Integer bargainBonusCycleLimit;

    /**
     * 砍价发起周期单位
     */
    private String bargainInitCycleUnit;

    /**
     * 砍价发起周期次数
     */
    @NotEmpty(message = "砍价发起周期次数不能为空")
    private Integer bargainInitCycleCnt;

    /**
     * 砍价发起周期上限
     */
    @NotEmpty(message = "砍价发起周期上限不能为空")
    private Integer bargainInitCycleLimit;

    /**
     * 说明类型 0:文本 1:图文
     */
//    @Min(value = 0,message ="说明类型不合法")
    private Integer descType;

    /**
     * 活动说明
     */
    @NotEmpty(message = "活动说明不能为空")
    private String description;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 商品列表
     */
    @Valid
    @NotEmpty(message = "砍价商品不能为空")
    private List<BargainItemUpdateDTO> itemList;

    /**
     * 配置列表
     */
    @Valid
    @NotEmpty(message = "砍价商品不能为空")
    private List<BargainActivityConfigDTO> configList;
}
