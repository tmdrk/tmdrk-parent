package org.tmdrk.toturial.spring.es.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 属性库 实体
 *
 * @author William.Wei
 * @since 2019-09-11
 */
@Data
@Accessors(chain = true)
public class ProductAttrDTO implements Serializable {

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
     * 名称
     */
    private String attrName;

    /**
     * 类型 0-自定义/1-单选
     */
    private String attrValues;

    /**
     * 适用范围
     */
    private String scope;

    /**
     * 备注
     */
    private String remark;

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
     * 排序序号，越小越靠前
     */
    private Integer sortNumber;

    /**
     * 逻辑删除状态
     */
    private Boolean del;

}