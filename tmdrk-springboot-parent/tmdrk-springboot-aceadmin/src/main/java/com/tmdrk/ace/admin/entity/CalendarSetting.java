package com.tmdrk.ace.admin.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * (CalendarSetting)实体类
 *
 * @author zhoujie
 * @since 2021-01-27 14:02:12
 */
@Data
@Accessors(chain = true)
public class CalendarSetting implements Entity<Long> {

    /**
     * id
     */
    private Long id;

    /**
     * 日历id
     */
    @NotNull
    @Min(0)
    private Integer calendarId;

    /**
     * 日历日期 yyyy-MM-dd
     */
    @NotBlank(message = "日历日期")
    private String calendarDate;

    
    private Date createTime;

    
    private String createBy;

    
    private Date updateTime;

    
    private String updateBy;

    /**
     * 是否删除 0-否 1-是
     */
    private Boolean del;

}