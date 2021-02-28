package com.tmdrk.ace.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmdrk.ace.admin.entity.CalendarSetting;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * ICalendarSettingElasticsearchService
 *
 * @author Jie.Zhou
 * @date 2021/1/27 17:27
 */
public interface ICalendarSettingElasticsearchService {
    void index(CalendarSetting calendarSetting) throws JsonProcessingException;
    void remove(Long id);
    SearchResponse search(SearchSourceBuilder q) throws IOException;

    Long maxId();

    void update(CalendarSetting calendarSetting) throws JsonProcessingException;
}
