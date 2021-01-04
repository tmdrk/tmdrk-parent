package org.tmdrk.toturial.common.util.cache;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * BargainsInfoDTO
 *
 * @author Jie.Zhou
 * @date 2020/9/12 14:55
 */
@Data
@Accessors(chain = true)
public class BargainsInfoDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 关联活动id
     */
    private Long activityId;

    /**
     * 商品 item id
     */
    private Long itemId;

    /**
     * 砍价记录编码
     */
    private String bargainNo;

    /**
     * 商品 item_spu id
     */
    private Long itemSpuId;

    /**
     * 用户ID
     */
    private String customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 用户头像
     */
    private String headImg;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 砍价状态 0:砍价中、1:待领取、2:已领取、3待支付、4:砍价失败
     */
    private Integer status;

    /**
     * 商品spuCode
     */
    private String spuCode;

    /**
     * 商品spu名称
     */
    private String spuName;

    /**
     * 关联商品skuCode
     */
    private String skuCode;

    /**
     * 商品sku名称
     */
    private String skuName;

    /**
     * 商品主图片链接
     */
    private String mainPic;

    /**
     * 砍价限定时间（秒）
     */
    private Integer limitTime;

    /**
     * 原价
     */
    private Integer price;

    /**
     * 目标价
     */
    private Integer bargainPrice;

    /**
     * 已砍价
     */
    private Integer bargainedPrice;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * expireTime砍价过期时间
     */
    private Long expireTime;

    /**
     * 帮砍总金额 （原价-底价）
     */
    private Integer helpTotalAmt;

    /**
     * 剩余砍价金额
     */
    private Integer surplusAmt;

    /**
     * 剩余砍价人数
     */
    private Integer surplusCnt;

    /**
     * 是否自己帮砍 0：否 1：是
     */
    private Integer selfStatus;

    /**
     * 帮砍助力状态 0：帮砍失败 1：帮砍成功 2：砍价已过期 3：已经帮砍过 4：帮砍次数已用完 5：帮砍已结束 6:活动不存在或已结束,配置不存在 7:用户正在帮砍中
     */
    private Integer helpStatus;

    /**
     * 帮砍金额
     */
    private Integer reducePrice;

    /**
     * 赠品名称
     */
    private String giftName;

    /**
     * 赠品金额
     */
    private Integer giftAmt;

    /**
     * 活动状态 0:进行中 1:已结束
     */
    private Integer activityStatus;

    /**
     * 新会员数量
     */
    private Integer newCnt;

    /**
     * 新开卡数量
     */
    private Integer newCardCnt;

    /**
     * 成单是否需要开卡 0:不需要 1:需要
     */
    private Integer isNeedNewCard;

    /**
     * 剩余库存
     */
    private Integer surplusStore;

    /**
     * 活动说明
     */
    private String description;

    /**
     * 帮砍用户类型，0：新会员 1：新用户 2：老用户
     */
    private Integer helpUserType;
}
