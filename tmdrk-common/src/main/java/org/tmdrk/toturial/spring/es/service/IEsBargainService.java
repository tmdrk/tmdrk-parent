package org.tmdrk.toturial.spring.es.service;

import org.tmdrk.toturial.spring.es.IPage;
import org.tmdrk.toturial.spring.es.dto.BargainQueryDTO;
import org.tmdrk.toturial.spring.es.dto.Bargains;

import java.util.List;

/**
 * IEsBargainService
 *
 * @author Jie.Zhou
 * @date 2020/9/23 18:02
 */
public interface IEsBargainService extends IEsBaseService<Bargains> {

    /**
     * 新增
     *
     * @param entity
     */
    void put(Bargains entity);

    /**
     * 批量新增
     *
     * @param list
     */
    void putAll(List<Bargains> list);

    /**
     * 删除
     */
    void deleteAll();

    /**
     * 索引id查询
     *
     * @param indexId
     * @return
     */
    Bargains selectByIndexId(String indexId);

    /**
     * 查询列表
     *
     * @param query 查询条件对象
     * @param page  分页条件
     * @return 实体列表
     */
    IPage<Bargains> selectPage(BargainQueryDTO query, IPage<Bargains> page);

    /**
     * 查询列表
     *
     * @param query
     * @return
     */
    List<Bargains> selectList(BargainQueryDTO query);

    /**
     * processor批量新增
     *
     * @param list
     */
    void putAllProcessor(List<Bargains> list);

    Long updateByIndexId(Bargains t);

    Long update(Bargains t);
}
