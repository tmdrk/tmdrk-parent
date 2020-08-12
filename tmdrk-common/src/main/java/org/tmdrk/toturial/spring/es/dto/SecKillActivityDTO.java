package org.tmdrk.toturial.spring.es.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 秒杀活动
 *
 * @author William
 */
@Data
public class SecKillActivityDTO implements Serializable {

    /**
     * 活动ID
     */
    private Long id;

    /**
     * SPU编码
     */
    private String spuCode;

    /**
     * SKU编码
     */
    private String skuCode;

    /**
     * 结束时间
     */
    private Date startTime;

    /**
     * 开始时间
     */
    private Date endTime;

    /**
     * 秒杀价
     */
    private Integer pointPrice;

    /**
     * 活动库存
     */
    private Integer activityStock;

    /**
     * 限购数量
     */
    private Integer limited;

    /**
     * 已秒杀库存
     */
    private Integer skillStock;

    /**
     * 剩余库存
     */
    private Integer surplusStock;

    /**
     * 排序
     */
    private Integer sort;

}
