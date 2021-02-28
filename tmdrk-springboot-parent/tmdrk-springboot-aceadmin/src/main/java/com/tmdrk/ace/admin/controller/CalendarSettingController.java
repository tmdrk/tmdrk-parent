package com.tmdrk.ace.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmdrk.ace.admin.entity.CalendarSetting;
import com.tmdrk.ace.admin.entity.Result;
import com.tmdrk.ace.admin.service.ICalendarSettingElasticsearchService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * (CalendarSetting)表控制层
 *
 * @author zhoujie
 * @since 2021-01-27 14:02:12
 */
@RestController
@RequestMapping("/calendar-setting")
public class CalendarSettingController {
    /**
     * 服务对象
     */
    @Resource
    private ICalendarSettingElasticsearchService calendarSettingElasticsearchService;

    /**
      * 保存
      */
    @PostMapping
    public Result<Void> save(@Validated @RequestBody CalendarSetting calendarSetting){
        Long id = calendarSettingElasticsearchService.maxId();
        calendarSetting.setId(id==null?1L:id+1);
        calendarSetting.setCreateBy("创建人");
        calendarSetting.setCreateTime(new Date());
        calendarSetting.setDel(false);
        try {
            calendarSettingElasticsearchService.index(calendarSetting);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Result.failure(5001,"新增索引失败");
        }
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@Validated @RequestBody CalendarSetting calendarSetting){
        calendarSetting.setUpdateBy("更新人");
        calendarSetting.setUpdateTime(new Date());
        try {
            calendarSettingElasticsearchService.update(calendarSetting);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Result.failure(5002,"更新索引失败");
        }
        return Result.success();
    }
}