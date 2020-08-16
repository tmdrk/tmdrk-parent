package org.tmdrk.toturial.spring.es.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 销量更新
 *
 * @author William.Wei
 * @since 2019-08-29
 */
@Data
@Accessors(chain = true)
public class ProductSalesVolumeDTO implements Serializable {

    /**
     * SPU ID
     */
    private Long spuId;

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
}