package com.tmdrk.ace.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmdrk.ace.admin.TmdrkSpringbootAceadminApplication;
import com.tmdrk.ace.admin.entity.CalendarSetting;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;

/**
 * CalendarSettingElasticsearchServiceTest
 *
 * @author Jie.Zhou
 * @date 2021/2/7 14:39
 */
@Slf4j
@SpringBootTest(classes = TmdrkSpringbootAceadminApplication.class)
@RunWith(SpringRunner.class)
public class CalendarSettingElasticsearchServiceTest {
    @Autowired
    private ICalendarSettingElasticsearchService calendarSettingElasticsearchService;
    @Test
    public void index() {
        for(long i=0;i<9;i++){
            CalendarSetting calendarSetting = new CalendarSetting();
            calendarSetting
                    .setId(i)
                    .setCalendarId(12)
                    .setCalendarDate("2021-02-0"+(i+1))
                    .setCreateBy("系统"+i)
                    .setCreateTime(new Date())
                    .setDel(false);
            try {
                calendarSettingElasticsearchService.index(calendarSetting);
            } catch (JsonProcessingException e) {
                log.info("添加索引失败",e);
            }
        }
    }
    @Test
    public void maxId() {
        Long id = calendarSettingElasticsearchService.maxId();
        System.out.println("id="+id);
    }
}

