package org.tmdrk.toturial.spring.es;


import com.alibaba.fastjson.JSONObject;

/**
 * The interface Es base service.
 *
 * @param <T> the type parameter
 * @param <Q> the type parameter
 */
public interface IEsBaseService<T extends Entity<Long>, Q> {

    /**
     * Save long.
     * 增
     *
     * @param t the t
     * @return the long
     */
    Long save(T t);

    /**
     * Del long.
     * 删
     *
     * @param id the id
     * @return the long
     */
    Long del(Long id);

    /**
     * Exist boolean.
     * 指定文档是否存在
     *
     * @param id the id
     * @return the boolean
     */
    Boolean exist(Long id);

    /**
     * Update long.
     * 改
     *
     * @param t the t
     * @return the long
     */
    Long update(T t);

    /**
     * Script update long.
     * 脚本更新
     *
     * @param t      the t
     * @param script the script
     * @return the long
     */
    Long scriptUpdate(T t, JSONObject script);

    /**
     * Update by query long.
     * 条件更新
     *
     * @param script the t
     * @param q the q
     * @return the long
     */
    Long scriptUpdateByQuery(Q q, JSONObject script);

    /**
     * Bulk.
     * bulk 批量操作
     *
     * @param jsonBody the json body
     */
    void bulk(String jsonBody);

    /**
     * Details t.
     * 详情
     *
     * @param id the id
     * @return the t
     */
    T details(Long id);

    /**
     * Search list.
     * 查询
     *
     * @param q    the q
     * @param page the page
     * @return the list
     */
    IPage<T> search(Q q, IPage<T> page);

}
