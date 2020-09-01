package com.tmdrk.ace.admin.mapper;

import com.tmdrk.ace.admin.entity.MarketingGameDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (MarketingGameDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-01 21:11:51
 */
public interface MarketingGameDetailMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MarketingGameDetail selectById(Long id);

    /**
     * 分页查询
     *
     * @param start 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MarketingGameDetail> selectPage(@Param("start") int start, @Param("limit") int limit);

    /**
     * 查询全部
     *
     * @return 对象列表
     */
    List<MarketingGameDetail> selectAll();

    /**
     * 通过实体作为筛选条件查询
     *
     * @param marketingGameDetail 实例对象
     * @return 对象列表
     */
    List<MarketingGameDetail> selectList(MarketingGameDetail marketingGameDetail);

    /**
     * 新增数据
     *
     * @param marketingGameDetail 实例对象
     * @return 影响行数
     */
    int insert(MarketingGameDetail marketingGameDetail);

    /**
     * 批量新增
     *
     * @param marketingGameDetails 实例对象的集合
     * @return 影响行数
     */
    int batchInsert(List<MarketingGameDetail> marketingGameDetails);

    /**
     * 修改数据
     *
     * @param marketingGameDetail 实例对象
     * @return 影响行数
     */
    int update(MarketingGameDetail marketingGameDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();

    int updateBatch(List<MarketingGameDetail> marketingGameDetails);
}