package com.tmdrk.ace.admin.service.transaction;

import com.tmdrk.ace.admin.entity.Area;
import com.tmdrk.ace.admin.entity.MarketingGameDetail;
import com.tmdrk.ace.admin.entity.OmpAddress;

/**
 * TransactionService
 *
 * @author Jie.Zhou
 * @date 2021/6/4 13:08
 */
public interface TransactionService {
    void testRequire(MarketingGameDetail marketingGameDetail, OmpAddress ompAddress, Area area);
    void testRequireNew(MarketingGameDetail marketingGameDetail, OmpAddress ompAddress, Area area);
}
