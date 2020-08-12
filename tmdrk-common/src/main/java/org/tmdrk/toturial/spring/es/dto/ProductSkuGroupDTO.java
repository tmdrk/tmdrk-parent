package org.tmdrk.toturial.spring.es.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * SKU组合 实体
 *
 * @author William.Wei
 * @since 2019-09-06
 */
@Data
@Accessors(chain = true)
public class ProductSkuGroupDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * SPU ID
     */
    private Long spuId;

    /**
     * SPU编码
     */
    private String spuCode;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * SKU编码
     */
    private String skuCode;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * 组合的SKU ID
     */
//    @NotNull(message = "请确认组合SKU")
    private Long groupSkuId;

    /**
     * 组合的SKU 编码
     */
//    @NotNull(message = "请确认组合SKU编码")
    private String groupSkuCode;

    /**
     * 组合的SKU 名称
     */
//    @NotNull(message = "请确认组合SKU名称")
    private String groupSkuName;

    /**
     * 组合的SKU 数量
     */
//    @NotNull(message = "组合SKU 数量不能为空")
//    @Min(value = 1, message = "组合SKU数量不正确")
    private Integer groupSkuNum;

    /**
     * 组合的SKU 价格
     */
//    @NotNull(message = "请确认组合SKU价格")
    private Integer groupSkuSalePrice;

    /**
     * 组合的SKU 市场价格
     */
//    @NotNull(message = "请确认组合SKU市场价格")
    private Integer groupSkuMarketPrice;

    /**
     * 是否赠品 0-否/1-是
     */
//    @NotNull(message = "确定是否为赠品")
    private Boolean gift;

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

}