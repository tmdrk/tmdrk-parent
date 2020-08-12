package org.tmdrk.toturial.spring.es.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * SKU 实体
 *
 * @author William.Wei
 * @since 2019-08-29
 */
@Data
@Accessors(chain = true)
public class ProductSkuDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 平台
     */
    private String platform;

    /**
     * 平台SKU编码
     */
    private String platformSkuCode;

    /**
     * E生活档案编码
     */
    private String storeCode;

    /**
     * SPU编码
     */
    private Long spuId;

    /**
     * SPU编码
     */
    private String spuCode;

    /**
     * SKU编码
     */
    private String skuCode;

    /**
     * SKU商品名称
     */
    private String skuName;

    /**
     * 属性组
     */
//    @NotEmpty(message = "属性组不能为空")
//    @Size(max = 1024)
    private String attrs;

    /**
     * 分期数
     */
    private Integer stages;

    /**
     * 分期费率
     */
    private BigDecimal stagesRate;

    /**
     * 积分价格
     */
//    @NotNull(message = "积分价格不能为空")
    private Integer pointPrice;

    /**
     * 趣豆价格
     */
    private Integer beanPrice = 0;

    /**
     * 特殊积分价格
     */
    private Integer specialPointPrice = 0;

    /**
     * 特殊趣豆价格
     */
    private Integer specialBeanPrice = 0;

    /**
     * 售价
     */
    private Integer salePrice = 0;

    /**
     * 会员售价
     */
    private Integer vipPrice = 0;

    /**
     * 市场价
     */
//    @NotNull(message = "市场价不能为空")
    private Integer marketPrice;

    /**
     * 采购价
     */
//    @NotNull(message = "请确定采购价格")
    private Integer purchasePrice;

    /**
     * 税率
     */
    private BigDecimal taxRate = new BigDecimal(0);

    /**
     * 税号
     */
    private String taxNo = "";

    /**
     * 提成、反利、回佣类型
     */
    private Integer rebatesType = 0;

    /**
     * 提成、反利、回佣率
     */
    private BigDecimal rebatesRate = new BigDecimal(0);

    /**
     * 提成、反利、回佣金额
     */
    private Integer rebatesAmount = 0;

    /**
     * 重量
     */
    private Integer weight = 0;

    /**
     * 是否3C 0-否/1-是
     */
    private Boolean ccc;

    /**
     * 是否组合商品 0-否/1-是
     */
    private Boolean group;

    /**
     * 单订单限制数量
     */
//    @NotNull(message = "单订单限制数量")
    private Integer orderLimit;

    /**
     * 参与的秒杀活动
     */
    private String secKill;

    /**
     * SKU库存数量
     */
    private Integer skuStock;

    /**
     * SKU锁定库存数量
     */
    private Integer skuLockStock;

    /**
     * SKU销量
     */
    private Integer skuSalesVolume;

    /**
     * 同步状态
     */
    private String syncStatus;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除状态
     */
    private Boolean del;

    /**
     * 供应商sku备注
     */
    private String supplierSkuRemark;

    /**
     * 供应商SKU编码
     */
    private String supplierSkuCode;

    /**
     * SKU图片
     */
    private String skuPic;

    /**
     * 组合商品SKU
     */
//    @Valid
    List<ProductSkuGroupDTO> skuGroupList;

}