package com.tmdrk.myboot.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * BargainItemUpdateDTO
 *
 * @author Jie.Zhou
 * @date 2020/9/9 10:51
 */
@Data
@Accessors(chain = true)
public class BargainItemUpdateDTO {
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
    @NotEmpty(message = "商品sku不能为空")
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
     * 单用户限购数量
     */
    private Integer limitCnt;

    /**
     * 商品原价格
     */
    @Min(value = 0,message ="商品原价不合法")
    private Integer price;

    /**
     * 底价
     */
    @Min(value = 0,message ="商品底价不合法")
    private Integer bargainPrice;

    /**
     * 供应商与银行的结算价格
     */
    private Integer settlementPrice;

    /**
     * 砍价总库存
     */
    @Min(value = 0,message ="总库存不合法")
    private Integer totalStore;

    /**
     * 可用库存
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
     * 创建人
     */
    private String createBy;

    /**
     * 修改人
     */
    private String updateBy;
}
