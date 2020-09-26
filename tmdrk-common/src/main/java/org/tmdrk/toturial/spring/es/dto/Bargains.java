package org.tmdrk.toturial.spring.es.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.tmdrk.toturial.spring.es.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 砍价记录表(Bargains)实体类
 *
 * @author zhoujie
 * @since 2020-09-14 17:55:57
 */
@Data
@Accessors(chain = true)
public class Bargains implements Serializable,Entity<Long>, EsEntity<String> {
    private String indexId;

    /**
     * id
     */
    private Long id;

    /**
     * 关联活动id
     */
    private Long activityId;

    /**
     * 商品id
     */
    private Long itemId;

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
     * 砍价状态 0:砍价中、1:待领取、2待支付、3:已领取、4:砍价失败
     */
    private Integer status;

    /**
     * 关联商品skuCode
     */
    private String skuCode;

    /**
     * 商品名称
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
     * 收货人
     */
    private String receiverName;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 收货人手机号
     */
    private String mobile;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 数据状态是否删除(0否1是)
     */
    private Boolean del;

}
