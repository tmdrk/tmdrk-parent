package org.tmdrk.toturial.spring.es.service;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.tmdrk.toturial.spring.es.IPage;
import org.tmdrk.toturial.spring.es.dto.Bargains;
import org.tmdrk.toturial.spring.es.dto.EsEntity;

import java.util.List;

/**
 * The interface Es base service.
 *
 * @param <T> the type parameter
 */
public interface IEsBaseService<T extends EsEntity<String>> {

    /**
     * 新增
     *
     * @param index
     * @param entity
     */
    void put(String index, T entity);

    /**
     * 批量新增
     *
     * @param index
     * @param list
     */
    void putAll(String index, List<T> list);

    /**
     * processor批量新增
     *
     * @param list
     */
    void putAllProcessor(String index,List<Bargains> list);

    /**
     * 删除
     *
     * @param index
     */
    void delete(String index);

    /**
     * 按条件删除
     *
     * @param index
     * @param builder
     */
    void deleteByQuery(String index, QueryBuilder builder);

    /**
     * 索引id查询
     *
     * @param indexName
     * @param c
     * @param indexId
     * @return
     */
    T getByIndexId(String indexName, Class<T> c, String indexId);

    /**
     * 索引id删除
     *
     * @param indexName
     * @param indexId
     */
    void deleteByIndexId(String indexName, String indexId);

    /**
     * 查询集合
     *
     * @param index
     * @param builder
     * @param c
     * @return
     */
    List<T> search(String index, SearchSourceBuilder builder, Class<T> c);

    /**
     * 查询分页
     *
     * @param index
     * @param builder
     * @param c
     * @param page
     * @return
     */
    IPage<T> searchPage(String index, SearchSourceBuilder builder, Class<T> c, IPage<T> page);

    /**
     * 更新根据id
     *
     * @param t
     */
    Long update(String index,String id,T t);

    /**
     * 更新根据查询
     *
     * @param t
     */
    Long updateByQuery(String index,T t, QueryBuilder builder);

    boolean existsIndex(String index);
}
