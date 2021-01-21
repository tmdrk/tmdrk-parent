package com.tmdrk.ace.admin.export.mapping;

import lombok.Data;

@Data
public class JdAddressMapping {
    /**
     * 工行地址名称
     */
    private String name;
    /**
     * 京东地址名称
     */
    private String jdName;
    /**
     * 工行地址级别
     */
    private Integer level;
}