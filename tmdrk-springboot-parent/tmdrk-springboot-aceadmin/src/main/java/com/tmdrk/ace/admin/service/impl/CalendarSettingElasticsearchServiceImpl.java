package com.tmdrk.ace.admin.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmdrk.ace.admin.entity.CalendarSetting;
import com.tmdrk.ace.admin.service.ICalendarSettingElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * CalendarSettingElasticsearchServiceImpl
 *
 * @author Jie.Zhou
 * @date 2021/1/27 17:31
 */
@Service
@Slf4j
public class CalendarSettingElasticsearchServiceImpl implements ICalendarSettingElasticsearchService {
    @Value("${calendar-setting.index}")
    private String calendarSettingIndex;
    @Value("${calendar-setting.mapping}")
    private String calendarSettingMapping;
    @Resource
    private RestHighLevelClient restHighLevelClient;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectMapper objectMapperNoNull = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private ActionListener<IndexResponse> indexResponseActionListener = new ActionListener<IndexResponse>() {
        @Override
        public void onResponse(IndexResponse o) {
            log.info("添加日历活动管理{}索引成功{}", o.getId(), o.getResult());
        }

        @Override
        public void onFailure(Exception e) {
            log.error("添加日历活动索引失败", e);
        }
    };

    private ActionListener<DeleteResponse> deleteResponseActionListener = new ActionListener<DeleteResponse>() {
        @Override
        public void onResponse(DeleteResponse o) {
            log.info("删除日历活动管理{}索引成功{}", o.getId(), o.getResult());
        }

        @Override
        public void onFailure(Exception e) {
            log.error("删除日历活动管理索引失败", e);
        }
    };

    private ActionListener<UpdateResponse> updateResponseActionListener = new ActionListener<UpdateResponse>() {
        @Override
        public void onResponse(UpdateResponse o) {
            log.info("更新日历活动管理{}索引成功{}", o.getId(), o.getResult());
        }

        @Override
        public void onFailure(Exception e) {
            log.error("更新日历活动管理索引失败", e);
        }
    };

    @Override
    public void index(CalendarSetting calendarSetting) throws JsonProcessingException {
        restHighLevelClient.indexAsync(new IndexRequest(calendarSettingIndex).id(calendarSetting.getId().toString())
                .source(objectMapper.writeValueAsString(calendarSetting), XContentType.JSON)
                .opType(DocWriteRequest.OpType.INDEX), RequestOptions.DEFAULT, indexResponseActionListener);
    }

    @Override
    public void remove(Long id) {
        restHighLevelClient.deleteAsync(new DeleteRequest(calendarSettingIndex).id(id.toString()), RequestOptions.DEFAULT, deleteResponseActionListener);
    }

    @Override
    public SearchResponse search(SearchSourceBuilder q) throws IOException {
        if (log.isDebugEnabled()) {
            log.info("search: {}", q.toString());
        }
        return restHighLevelClient.search(new SearchRequest(calendarSettingIndex)
                .source(q), RequestOptions.DEFAULT);
    }

    @Override
    public Long maxId() {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();
        searchSourceBuilder.size(0);
        searchSourceBuilder.aggregation(AggregationBuilders.max("maxId").field("id"));
        System.out.println(searchSourceBuilder.toString());
        try {
            SearchResponse response = restHighLevelClient.search(new SearchRequest(calendarSettingIndex)
                    .source(searchSourceBuilder), RequestOptions.DEFAULT);
            Double maxId  = ((Max)response.getAggregations().get("maxId")).getValue();
            if(maxId > 0){
                return maxId.longValue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(CalendarSetting calendarSetting) throws JsonProcessingException {
        UpdateRequest updateRequest = new UpdateRequest(calendarSettingIndex, calendarSetting.getId().toString())
                .doc(objectMapperNoNull.writeValueAsString(calendarSetting), XContentType.JSON);
        updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        log.info(updateRequest.toString());
        restHighLevelClient.updateAsync(updateRequest, RequestOptions.DEFAULT, updateResponseActionListener);
    }

    @PostConstruct
    public void init() throws IOException {
        objectMapper.enable(WRITE_DATES_AS_TIMESTAMPS);
        if (!restHighLevelClient.indices().exists(new GetIndexRequest(calendarSettingIndex), RequestOptions.DEFAULT)) {
            restHighLevelClient.indices().create(new CreateIndexRequest(calendarSettingIndex)
                    .settings(Settings.builder()
                            .put("number_of_shards", 1)
                            .put("number_of_replicas", 1))
                    .mapping(calendarSettingMapping, XContentType.JSON), RequestOptions.DEFAULT);
        } else {
            restHighLevelClient.indices().putMapping(new PutMappingRequest(calendarSettingIndex)
                    .source(calendarSettingMapping, XContentType.JSON), RequestOptions.DEFAULT);
        }
    }
}
