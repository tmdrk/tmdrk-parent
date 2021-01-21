package com.tmdrk.ace.admin.export.omp;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * OmpAddressExport
 *
 * @author Jie.Zhou
 * @date 2021/1/20 14:36
 */
@Data
public class OmpAddressExport {
    @ExcelProperty(value = "编号", index = 0)
    private Long id;
    @ExcelProperty(value = "编码", index = 1)
    private String code;
    @ExcelProperty(value = "名称", index = 2)
    private String name;
    @ExcelProperty(value = "简称", index = 3)
    private String shortName;
    @ExcelProperty(value = "父编码", index = 4)
    private String parentCode;
    @ExcelProperty(value = "级别", index = 5)
    private Integer level;
    @ExcelProperty(value = "创建时间", index = 6)
    private Date createdTime;
    @ExcelIgnore
    private String pathName;
    @ExcelIgnore
    private Integer mapperId;
    @ExcelIgnore
    private Integer isDel;
    @ExcelIgnore
    private String createdBy;
    @ExcelIgnore
    private String updateBy;
    @ExcelIgnore
    private Date updateTime;
}
