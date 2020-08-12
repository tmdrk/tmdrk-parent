package org.tmdrk.toturial.spring.es.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.tmdrk.toturial.spring.es.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * SPU 实体
 *
 * @author William.Wei
 * @since 2019 -08-29
 */
@Data
@Accessors(chain = true)
public class ProductSpuDTO implements Serializable, Entity<Long> {

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
     * 平台 注意与实体中字段名与类型不一样
     * 方便ES查询
     */
    private List<String> platforms;

    /**
     * 平台SPU编码
     */
    private String platformSpuCode;

    /**
     * E生活档案编码
     */
    private String storeCode;

    /**
     * 编码
     */
    private String spuCode;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 品牌名称
     */
    private String brandName;

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
     * ……
     */
    private Integer specialScope;

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
     * 副品名
     */
    private String spuSubName;

    /**
     * 支付方式
     */
    private Integer payment;

    /**
     * 金豆抵扣支持
     */
    private Integer beanSupport;

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
    private Integer pointPrice;

    /**
     * 趣豆价格
     */
    private Integer beanPrice;

    /**
     * 特殊积分价格
     */
    private Integer specialPointPrice;

    /**
     * 特殊趣豆价格
     */
    private Integer specialBeanPrice;

    /**
     * 售价，总售价与分期无关
     */
    private Integer salePrice;

    /**
     * 会员售价
     */
    private Integer vipPrice;

    /**
     * 市场价
     */
    private Integer marketPrice;

    /**
     * 视频
     */
    private String video;

    /**
     * 主图
     */
    private String mainPic;

    /**
     * 媒体信息（视频或多图）
     */
    private String mediaInfo;

    /**
     * 详情 富文本
     */
    private String details;

    /**
     * 支持服务
     */
    private String supportService;

    /**
     * 购物须知
     */
    private String readme;

    /**
     * 是否3C
     */
    private Boolean ccc;

    /**
     * 条码
     */
    private String barCode;

    /**
     * 二维码
     */
    private String qrCode;

    /**
     * 是否组合商品 0-否/1-是
     */
    private Boolean group;

    /**
     * 是否代发 供应商发货
     */
    private Boolean supplierSend;

    /**
     * 已上架渠道编码集 eg:['C20159']
     */
    private String channelList;

    /**
     * 标签 eg:新品,推荐
     */
    private String labels;

    /**
     * 标签 eg:新品,推荐
     */
    private List<String> labelList;

    /**
     * 主题
     */
    private String themes;

    /**
     * 参与的秒杀活动
     */
    private String secKill;

    /**
     * 产品经理ID
     */
    private Long productUserId;

    /**
     * 产品经理名称
     */
    private String productUserName;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 供应商工行商户编号
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 审核状态
     */
    private String auditStatus;

    /**
     * 审核说明
     */
    private String auditDesc;

    /**
     * 同步状态
     */
    private String syncStatus;

    /**
     * 销售状态 shelve/unshelve
     */
    private String saleStatus;

    /**
     * 上架时间
     */
    private Date shelveTime;

    /**
     * 总库存数量
     */
    private Integer totalStock;

    /**
     * 总销量
     */
    private Integer totalSalesVolume;

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
     * 账号类型
     */
    private String accountType;

    /**
     * 账号校验正则
     */
    private String accountReg;

    /**
     * 账号错误提示
     */
    private String accountWarn;

    /**
     * 产地编码
     */
    private String placeCode;

    /**
     * 产地名称
     */
    private String placeName;

    /**
     * 分类ID表 1级-2级-3级
     */
    private List<String> categoryIds;
}