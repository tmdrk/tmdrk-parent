package org.tmdrk.toturial.spring.es;

import org.tmdrk.toturial.spring.es.dto.ProductSalesVolumeDTO;
import org.tmdrk.toturial.spring.es.dto.ProductSpuEsDTO;
import org.tmdrk.toturial.spring.es.dto.ProductSpuQueryDTO;

import java.util.Collection;

/**
 * The interface Es product spu service.
 *
 * @author William
 */
public interface IEsProductSpuService extends IEsBaseService<ProductSpuEsDTO, ProductSpuQueryDTO> {
    /**
     * Add sales volume.
     * 增加销量
     *
     * @param updateDTO the update dto
     */
    void addSalesVolume(ProductSpuEsDTO updateDTO);

    /**
     * Rebuild index.
     * 索引重建 所有商品
     */
    void rebuildIndex();

    /**
     * Rebuild index.
     * 索引重建 指定商品
     *
     * @param spuIds the spu ids
     */
    void rebuildIndex(String spuIds);

    /**
     * Reset month sales volume.
     * 重置月销量
     */
    void resetMonthSalesVolume();

    /**
     * Sets recent sales volume.
     * 销量更新
     *
     * @param salesVolumeDTOS the sales volume dtos
     * @param reset
     */
    void setRecentSalesVolume(Collection<ProductSalesVolumeDTO> salesVolumeDTOS, boolean reset);
}
