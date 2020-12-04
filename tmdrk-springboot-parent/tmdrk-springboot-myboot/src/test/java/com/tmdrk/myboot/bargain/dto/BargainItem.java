package com.tmdrk.myboot.bargain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 砍价商品表(BargainItem)实体类
 *
 * @author zhoujie
 * @since 2020-09-16 13:24:48
 */
@Data
@Accessors(chain = true)
public class BargainItem implements Entity<Long> {

    /**
     * id
     */
    private Long id;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * spu_id
     */
    private Long spuId;

    /**
     * 商品spu编码
     */
    private String spuCode;

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
     * 属性组
     */
    private String attrs;

    /**
     * 单用户限购数量
     */
    private Integer limitCnt;

    /**
     * 商品原价格
     */
    private Integer price;

    /**
     * 底价
     */
    private Integer bargainPrice;

    /**
     * 供应商与银行的结算价格
     */
    private Integer settlementPrice;

    /**
     * 砍价总库存
     */
    private Integer totalStore;

    /**
     * 剩余库存
     */
    private Integer surplusStore;

    /**
     * 锁定库存
     */
    private Integer lockStore;

    /**
     * 已使用库存
     */
    private Integer usedStore;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 数据状态是否删除(0否1是)
     */
    private Boolean del;

}
