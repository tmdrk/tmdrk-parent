package com.tmdrk.ace.admin.service.impl;

import com.tmdrk.ace.admin.entity.MarketingGameDetail;
import com.tmdrk.ace.admin.mapper.MarketingGameDetailMapper;
import com.tmdrk.ace.admin.service.MarketingGameDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * (MarketingGameDetail表)服务实现类
 *
 * @author makejava
 * @since 2020-09-01 21:16:59
 */
@Service("marketingGameDetailService")
public class MarketingGameDetailServiceImpl implements MarketingGameDetailService {
    @Resource
    private MarketingGameDetailMapper marketingGameDetailMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MarketingGameDetail selectById(Long id) {
        return this.marketingGameDetailMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param start 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<MarketingGameDetail> selectPage(int start, int limit) {
        return this.marketingGameDetailMapper.selectPage(start, limit);
    }

    /**
     * 查询所有
     *
     * @return 实例对象的集合
     */
    @Override
    public List<MarketingGameDetail> selectAll() {
        return this.marketingGameDetailMapper.selectAll();
    }

    /**
     * 根据条件查询
     *
     * @return 实例对象的集合
     */
    @Override
    public List<MarketingGameDetail> selectList(MarketingGameDetail marketingGameDetail) {
        return this.marketingGameDetailMapper.selectList(marketingGameDetail);
    }

    /**
     * 新增数据
     *
     * @param marketingGameDetail 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MarketingGameDetail marketingGameDetail) {
        return this.marketingGameDetailMapper.insert(marketingGameDetail);
    }

    /**
     * 批量新增
     *
     * @param marketingGameDetails 实例对象的集合
     * @return 生效的条数
     */
    @Override
    public int batchInsert(List<MarketingGameDetail> marketingGameDetails) {
        return this.marketingGameDetailMapper.batchInsert(marketingGameDetails);
    }

    /**
     * 修改数据
     *
     * @param marketingGameDetail 实例对象
     * @return 实例对象
     */
    @Override
    public MarketingGameDetail update(MarketingGameDetail marketingGameDetail) {
        this.marketingGameDetailMapper.update(marketingGameDetail);
        return this.selectById(marketingGameDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(Long id) {
        return this.marketingGameDetailMapper.deleteById(id);
    }

    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    @Override
    public int count() {
        return this.marketingGameDetailMapper.count();
    }

    @Override
    public int updateBatch(MarketingGameDetail marketingGameDetail) {
        List<MarketingGameDetail> marketingGameDetails = marketingGameDetailMapper.selectList(marketingGameDetail);
        Random random = new Random();
        marketingGameDetails.forEach(detail-> {
            detail.setAttrValue(detail.getAttrValue() + random.nextInt(10));
        });
        return marketingGameDetailMapper.updateBatch(marketingGameDetails);
    }


}