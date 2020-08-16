package org.tmdrk.toturial.spring.es.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * SPU参数
 *
 * @author William
 * @since 2020-02-28
 */
@Data
@Accessors(chain = true)
public class ProductSpuPropertyDTO implements Serializable {

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
     * 参数名
     */
//    @NotEmpty(message = "参数名称不能为空")
    private String propertyName;

    /**
     * 参数值
     */
//    @NotEmpty(message = "参数值不能为空")
    private String propertyValue;

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