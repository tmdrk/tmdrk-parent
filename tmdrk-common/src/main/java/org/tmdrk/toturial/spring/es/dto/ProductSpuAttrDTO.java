package org.tmdrk.toturial.spring.es.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * SPU属性 实体
 *
 * @author William.Wei
 * @since 2019-08-29
 */
@Data
@Accessors(chain = true)
public class ProductSpuAttrDTO implements Serializable {

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
     * 属性定义编码
     */
//    @NotNull(message = "属性定义编码不能为空", groups = {Group.Save.class, Group.Update.class})
    private Long attrNameId;

    /**
     * 属性名
     */
//    @NotEmpty(message = "属性名不能为空", groups = {Group.Save.class, Group.Update.class})
//    @Size(max = 255)
    private String attrNameDesc;

    /**
     * 属性类型 0/1 自定义
     */
//    @NotNull(message = "属性类型 0/1 自定义不能为空", groups = {Group.Save.class, Group.Update.class})
    private Integer attrNameType;

    /**
     * 属性编码
     */
//    @NotNull(message = "属性编码不能为空", groups = {Group.Save.class, Group.Update.class})
    private Long attrValueId;

    /**
     * 属性图标
     */
    private String attrValueIcon;

    /**
     * 属性值
     */
//    @NotEmpty(message = "属性值不能为空", groups = {Group.Save.class, Group.Update.class})
//    @Size(max = 255)
    private String attrValueDesc;

    /**
     * 属性别名
     */
    private String attrValueAlias;

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