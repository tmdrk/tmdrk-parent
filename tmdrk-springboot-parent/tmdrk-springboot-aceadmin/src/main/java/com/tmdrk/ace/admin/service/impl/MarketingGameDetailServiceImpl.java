package com.tmdrk.ace.admin.service.impl;

import com.tmdrk.ace.admin.entity.MarketingGameDetail;
import com.tmdrk.ace.admin.mapper.MarketingGameDetailMapper;
import com.tmdrk.ace.admin.service.MarketingGameDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
        List<MarketingGameDetail> details = new ArrayList<>();
        MarketingGameDetail det = new MarketingGameDetail();
        det.setGameId(1L);
        det.setAttrName("gameHaha");
        det.setAttrValue("888");
        details.add(det);
        List<MarketingGameDetail> insertList = new ArrayList<>();
        marketingGameDetails.forEach(d->{
            if(!details.contains(d.getAttrName())){

            }
        });


        Random random = new Random();
        int i = random.nextInt(10);
        marketingGameDetails.forEach(detail-> {
            detail.setAttrValue(detail.getAttrValue() + i);
        });
        System.out.println("批量更新开始");
        return marketingGameDetailMapper.updateBatch(marketingGameDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int testSavePoint() {
        MarketingGameDetail insert = new MarketingGameDetail();
        insert.setGameId(1L);
        insert.setAttrValue("100");
        insert.setAttrName("101");
        insert.setAttrDesc("test");
        insert.setCreateTime(new Date());
        insert.setIsDel(false);
        MarketingGameDetail update = new MarketingGameDetail();
        update.setId(54L);
        update.setAttrValue("2020-09-11 12:22:55");
        Object savePoint = null;
        try {
            insert(insert);
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
            update(update);
            int a = 10/0; //这里因为除数0会报异常,进入catch块
        }catch (Exception e){
            //手工回滚异常
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
        }
        return 0;
    }


}