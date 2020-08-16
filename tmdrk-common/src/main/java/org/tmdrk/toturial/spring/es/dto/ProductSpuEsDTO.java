package org.tmdrk.toturial.spring.es.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
public class ProductSpuEsDTO extends ProductSpuExtendDTO implements Serializable {

    /**
     * 平台 注意与实体中字段名与类型不一样
     * 方便ES查询
     */
    private List<String> platforms;

    /**
     * 产地编码
     */
    private List<String> placeCodes;

    /**
     * 产地名称
     */
    private List<String> placeNames;

    /**
     * 标签 eg:新品,推荐
     */
    private List<String> labelList;

    /**
     * 参与的秒杀活动列表
     */
    private List<SecKillActivityDTO> secKillList;

    /**
     * 商品所在主题列表
     */
    private List<ThemeDTO> themeList;

    /**
     * 服务器当前时间
     */
    private Date currentTime;

    /**
     * 客服电话
     */
    private String customerServicePhone;

    /**
     * 当月销量
     */
    private Integer monthSalesVolume;

    /**
     * 当天销量
     */
    private Integer daySalesVolume;

    /**
     * 最近销量
     */
    private Integer recentSalesVolume;

    /**
     * GT权重
     */
    private Integer gtWeight;
}