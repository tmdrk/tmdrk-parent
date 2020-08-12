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
public class ProductSpuExtendDTO extends ProductSpuDTO implements Serializable {

    /**
     * 分类路径名称
     */
    private String categoryPathName;

    /**
     * SPU属性
     */
    private List<ProductSpuAttrDTO> attrList;

    /**
     * SPU参数
     */
//    @Valid
    private List<ProductSpuPropertyDTO> propertyList;

    /**
     * SKU列表
     */
    private List<ProductSkuDTO> skuList;
}