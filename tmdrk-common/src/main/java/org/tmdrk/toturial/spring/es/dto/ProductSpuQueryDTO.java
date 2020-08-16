package org.tmdrk.toturial.spring.es.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * SPU 实体
 *
 * @author William.Wei
 * @since 2019 -08-29
 */
@Data
@Accessors(chain = true)
public class ProductSpuQueryDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 多ID查询条件
     */
    private String[] ids;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 平台
     */
    private String platform;

    /**
     * 平台SPU编码
     */
    private String platformSpuCode;

    /**
     * 平台SPU编码集合
     */
    private List<String> platformSpuCodes;

    /**
     * 编码
     */
    private String spuCode;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 多分类ID
     */
    private String[] categoryIds;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 多品牌查询条件
     */
    private String[] brandIds;

    /**
     * 特殊类型
     * 0 常规
     * 1 非常规
     */
    private Integer specialType;

    /**
     * 特殊范围
     * 0 常规
     * 1 秒杀
     * 2 爆品
     * 3 大转盘
     * 4 竞拍
     * ……
     */
    private Integer specialScope;

    /**
     * 多特殊范围查询
     */
    private String[] specialScopeIn;

    /**
     * 非多特殊范围查询
     */
    private String[] specialScopeNotIn;

    /**
     * 类型 0-虚拟/1-实物
     */
    private Integer spuType;

    /**
     * 类型 0-未定义/1-卡密/2-直充
     */
    private Integer virtualType;

    /**
     * 品名
     */
    private String spuName;

    /**
     * 标签 eg:新品,推荐
     */
    private String labels;

    /**
     * 主题
     */
    private String themes;

    /**
     * 参与的秒杀活动
     */
    private String secKill;

    /**
     * 供应商
     */
    private Long supplierId;

    /**
     * 多供应商
     */
    private String[] supplierIds;

    /**
     * 最小价格
     */
    private Integer minPrice;

    /**
     * 最大价格
     */
    private Integer maxPrice;

    /**
     * 是否3C 0-否/1-是
     */
    private Boolean ccc;

    /**
     * 是否组合商品 0-否/1-是
     */
    private Boolean group;

    /**
     * 多渠道查询
     */
    private String channelList;

    /**
     * 产品经理ID
     */
    private Long productUserId;

    /**
     * 审核状态
     */
    private String auditStatus;

    /**
     * 销售状态 shelve/unshelve
     */
    private String saleStatus;

    /**
     * 同步状态
     */
    private String syncStatus;

    /**
     * 分类路径查询
     */
    private String categoryPathName;

    /**
     * 逻辑删除状态
     */
    private Boolean del;

    /**
     * 最小销量
     */
    private Integer minMonthSalesVolume;

    /**
     * 最大销量
     */
    private Integer maxMonthSalesVolume;

}