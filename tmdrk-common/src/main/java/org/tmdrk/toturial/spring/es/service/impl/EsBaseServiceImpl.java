package org.tmdrk.toturial.spring.es.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.UpdateScript;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.tmdrk.toturial.spring.es.BusinessException;
import org.tmdrk.toturial.spring.es.IPage;
import org.tmdrk.toturial.spring.es.dto.Bargains;
import org.tmdrk.toturial.spring.es.dto.EsEntity;
import org.tmdrk.toturial.spring.es.service.IEsBaseService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.script.ScriptType.INLINE;

/**
 * @author longjianwei
 * @Description: es service
 * @date 2020/4/1117:40
 */
@Slf4j
@Service
public abstract class EsBaseServiceImpl<T extends EsEntity<String>> implements IEsBaseService<T> {

    @Autowired
    public RestHighLevelClient restHighLevelClient;

    /**
     * 新增
     *
     * @param index
     * @param entity
     */
    @Override
    public void put(String index, T entity) {
        IndexRequest request = new IndexRequest(index);
        request.id(entity.getIndexId());
        request.source(JSON.toJSONString(entity), XContentType.JSON);
        //写入完成立即刷新
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        try {
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es put error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量新增
     *
     * @param index
     * @param list
     */
    @Override
    public void putAll(String index, List<T> list) {
        BulkRequest request = new BulkRequest();
        list.forEach(item -> request.add(new IndexRequest(index).id(item.getIndexId())
                .source(JSON.toJSONString(item), XContentType.JSON)));
        //写入完成立即刷新
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es putAll error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量新增附带监听
     *
     * @param index
     * @param list
     */
    @Override
    public void putAllProcessor(String index,List<Bargains> list) {
        BulkProcessor bulkProcessor = init();
        IndexRequest request = null;
        for(Bargains bulk: list) {
            request = new IndexRequest("post");
            request.index(index).id(String.valueOf(bulk.getId())).source(list, XContentType.JSON);
            bulkProcessor.add(request);
        }
    }

    public BulkProcessor init() {
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                log.info("---尝试插入{}条数据---", request.numberOfActions());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                log.info("---尝试插入{}条数据成功---", request.numberOfActions());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                log.error("---尝试插入数据失败---", failure);
            }
        };

        return BulkProcessor.builder((request, bulkListener) ->
                restHighLevelClient.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), listener)
                .setBulkActions(10000)
                .setBulkSize(new ByteSizeValue(10, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(2)
                .build();
    }

    /**
     * 删除索引
     *
     * @param index
     */
    @Override
    public void delete(String index) {
        try {
            restHighLevelClient.indices().delete(new DeleteIndexRequest(index), RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es delete error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 按条件删除
     *
     * @param index
     * @param builder
     */
    @Override
    public void deleteByQuery(String index, QueryBuilder builder) {
        DeleteByQueryRequest request = new DeleteByQueryRequest(index);
        request.setQuery(builder);
        //设置批量操作数量,最大为10000
        request.setBatchSize(10000);
        request.setConflicts("proceed");
        try {
            restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("es deleteByQuery error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 索引id查询
     *
     * @param indexName
     * @param c
     * @param indexId
     * @return
     */
    @Override
    public T getByIndexId(String indexName, Class<T> c, String indexId) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("_id", indexId));
        List<T> list = search(indexName, builder, c);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 索引id删除
     *
     * @param indexName
     * @param indexId
     */
    @Override
    public void deleteByIndexId(String indexName, String indexId) {
        deleteByQuery(indexName, QueryBuilders.termQuery("_id", indexId));
    }


    /**
     * 查询集合
     *
     * @param index
     * @param builder
     * @param c
     * @return
     */
    @Override
    public List<T> search(String index, SearchSourceBuilder builder, Class<T> c) {
        SearchRequest request = new SearchRequest(index);
        request.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHit[]    hits     = response.getHits().getHits();
            List<T>        res      = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), c));
            }
            return res;
        } catch (Exception e) {
            log.error("es search error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsIndex(String index) {
        try{
            GetIndexRequest request = new GetIndexRequest(index);
            boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
            return exists;
        }catch (Exception e){
            log.error("未知错误:{}", e);
        }
        return false;
    }

    /**
     * 查询分页
     *
     * @param index
     * @param builder
     * @param c
     * @param page
     * @return
     */
    @Override
    public IPage<T> searchPage(String index, SearchSourceBuilder builder, Class<T> c, IPage<T> page) {
        SearchRequest request = new SearchRequest(index);
        builder = wrapperBuilder(builder, page);
        request.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHit[]    hits     = response.getHits().getHits();
            List<T>        res      = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), c));
            }
            page.setList(res);
            page.setTotal(response.getHits().getTotalHits().value);
            return page;
        } catch (Exception e) {
            log.error("es searchPage error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long updateByQuery(String index,T t, QueryBuilder builder) {
        //参数为索引名，可以不指定，可以一个，可以多个
        UpdateByQueryRequest request = new UpdateByQueryRequest(index);
        // 更新时版本冲突
        request.setConflicts("proceed");
        // 设置查询条件，第一个参数是字段名，第二个参数是字段的值
//        request.setQuery(new TermQueryBuilder("indexId", t.getIndexId()));
        request.setQuery(builder);
        // 更新最大文档数
        request.setSize(10);
        // 批次大小
        request.setBatchSize(1000);
//        request.setScript(new Script(ScriptType.INLINE, "painless",
//                "if (ctx._source.first == 'sam') {ctx._source.last = 'update';}", Collections.e request.setScript(new Script(ScriptType.INLINE, "painless",
        //组装更新字段
        StringBuilder idOrCode = getStringBuilder(t);
        request.setScript(new Script(INLINE, "painless",
                idOrCode.toString(), Collections.emptyMap()));
        // 并行
        request.setSlices(2);
        // 使用滚动参数来控制“搜索上下文”存活的时间
        request.setScroll(TimeValue.timeValueMinutes(10));
        // 如果提供路由，则将路由复制到滚动查询，将流程限制为匹配该路由值的切分
		//request.setRouting("=cat");
        // 可选参数
        // 超时
        request.setTimeout(TimeValue.timeValueMinutes(2));
        // 刷新索引
        request.setRefresh(true);
        try {
            BulkByScrollResponse response = restHighLevelClient.updateByQuery(request, RequestOptions.DEFAULT);
            return response.getStatus().getUpdated();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    private StringBuilder getStringBuilder(T t) {
        Class<? extends EsEntity> aClass = t.getClass();
        Field[] declaredFields = t.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for(Field field: declaredFields){
            field.setAccessible(true);
            String name = field.getName();
            String upperName = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
            try {
                Method m = aClass.getMethod("get"+upperName);
                m.setAccessible(true);
                Object invoke = m.invoke(t);
                if(invoke != null){
                    if(invoke instanceof Date){
                        sb.append("ctx._source.").append(name).append("='").append(((Date) invoke).getTime()).append("';");
                    }else{
                        sb.append("ctx._source.").append(name).append("='").append(invoke).append("';");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb;
    }

    private SearchSourceBuilder wrapperBuilder(SearchSourceBuilder builder, IPage<T> page) {
        builder.from(Integer.parseInt(String.valueOf((page.getPage() - 1) * page.getSize())));
        builder.size(Integer.parseInt(String.valueOf(page.getSize())));
        return builder;
    }

    @Override
    public Long update(String index, String id, T t) {
        UpdateRequest request = new UpdateRequest(index, id);
        StringBuilder idOrCode = getStringBuilder(t);
        Script script = new Script(INLINE, Script.DEFAULT_SCRIPT_LANG, idOrCode.toString(), Collections.emptyMap());
        request.script(script);
        try {
            UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            if(response.getResult().ordinal()==1){
                return 1L;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1L;
    }
}
