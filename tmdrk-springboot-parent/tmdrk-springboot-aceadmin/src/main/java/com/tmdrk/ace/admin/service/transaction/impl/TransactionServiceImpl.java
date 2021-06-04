package com.tmdrk.ace.admin.service.transaction.impl;

import com.tmdrk.ace.admin.entity.Area;
import com.tmdrk.ace.admin.entity.MarketingGameDetail;
import com.tmdrk.ace.admin.entity.OmpAddress;
import com.tmdrk.ace.admin.mapper.AreaMapper;
import com.tmdrk.ace.admin.service.MarketingGameDetailService;
import com.tmdrk.ace.admin.service.OmpAddressService;
import com.tmdrk.ace.admin.service.transaction.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * TransactionServiceImpl
 *
 * @author Jie.Zhou
 * @date 2021/6/4 13:08
 */
@Slf4j
@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {
    @Resource
    private MarketingGameDetailService marketingGameDetailService;
    @Resource
    private OmpAddressService ompAddressService;
    @Resource
    private AreaMapper areaMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void testRequire(MarketingGameDetail marketingGameDetail, OmpAddress ompAddress, Area area){
        areaMapper.updateByPrimaryKeySelective(area);
        try{
            ompAddressService.updateById(ompAddress);
        }catch (Exception e){
            // 如果子事务是require，子事务抛异常，上层事务即使捕获该异常，依然抛下面的异常，事务提交失败
            // Transaction rolled back because it has been marked as rollback-only
            log.info("更新异常");
        }
        marketingGameDetailService.update(marketingGameDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void testRequireNew(MarketingGameDetail marketingGameDetail, OmpAddress ompAddress, Area area){
        areaMapper.updateByPrimaryKeySelective(area);
        try{
            ompAddressService.updateById(ompAddress);
        }catch (Exception e){
            System.out.println("更新异常 "+e);
        }
        marketingGameDetailService.update(marketingGameDetail);
    }
}
