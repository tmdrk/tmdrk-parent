package org.tmdrk.toturial.spring.es.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Splitter;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.tmdrk.toturial.spring.es.IPage;
import org.tmdrk.toturial.spring.es.dto.BargainQueryDTO;
import org.tmdrk.toturial.spring.es.dto.Bargains;
import org.tmdrk.toturial.spring.es.service.IEsBargainService;

import java.util.List;

/**
 * IEsBargainServiceImpl
 *
 * @author Jie.Zhou
 * @date 2020/9/23 18:02
 */
@Service("esBargainService")
public class IEsBargainServiceImpl  extends EsBaseServiceImpl<Bargains> implements IEsBargainService {
    public static final String indexName = "bocfd_bargain";

    @Override
    public void put(Bargains entity) {
        super.put(indexName, entity);
    }

    @Override
    public void putAll(List<Bargains> list) {
        super.putAll(indexName, list);
    }

    @Override
    public void deleteAll() {
        super.deleteByQuery(indexName, QueryBuilders.boolQuery());
    }

    @Override
    public Bargains selectByIndexId(String indexId) {
        return super.getByIndexId(indexName, Bargains.class, indexId);
    }

    @Override
    public IPage<Bargains> selectPage(BargainQueryDTO query, IPage<Bargains> page) {
        SearchSourceBuilder builder = buildSearchSourceBuilder(query);
        super.searchPage(indexName, builder, Bargains.class, page);
        return page;
    }

    @Override
    public List<Bargains> selectList(BargainQueryDTO query) {
        SearchSourceBuilder builder = buildSearchSourceBuilder(query);
        return super.search(indexName, builder, Bargains.class);
    }

    private SearchSourceBuilder buildSearchSourceBuilder(BargainQueryDTO query) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StrUtil.isNotBlank(query.getSkuCode())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("skuCode", query.getSkuCode()));
        }
        if (query.getStatus() != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery("status", query.getStatus()));
        }
        if (StrUtil.isNotBlank(query.getCustomerId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("customerId", query.getCustomerId()));
        }
        if (query.getActivityId() != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery("activityId", query.getActivityId()));
        }
        if (query.getId() != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery("id", query.getId()));
        }
        builder.query(boolQueryBuilder);
        //设置排序
        if (StrUtil.isNotBlank(query.getDesc())) {
            Splitter.on(",").omitEmptyStrings().splitToList(query.getDesc()).stream().forEach(str -> {
                builder.sort(str, SortOrder.DESC);
            });
        }
        //升序字段
        if (StrUtil.isNotBlank(query.getAsc())) {
            Splitter.on(",").omitEmptyStrings().splitToList(query.getAsc()).stream().forEach(str -> {
                builder.sort(str, SortOrder.ASC);
            });
        }
        //默认id倒序
        builder.sort("id", SortOrder.DESC);
        return builder;
    }

    @Override
    public void putAllProcessor(List<Bargains> list) {
        super.putAllProcessor(indexName, list);
    }

    @Override
    public Long updateByIndexId(Bargains t) {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("_id", t.getIndexId());
        return super.updateByQuery(indexName,t,queryBuilder);
    }

    @Override
    public Long update(Bargains t) {
        return super.update(indexName,t.getIndexId(),t);
    }
}
