package com.tmdrk.ace.admin.service;

import com.tmdrk.ace.admin.TmdrkSpringbootAceadminApplication;
import com.tmdrk.ace.admin.entity.Area;
import com.tmdrk.ace.admin.entity.MarketingGameDetail;
import com.tmdrk.ace.admin.entity.OmpAddress;
import com.tmdrk.ace.admin.service.transaction.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * TransactionPropagationTest
 * 事务的传播特性测试
 * @author Jie.Zhou
 * @date 2021/6/4 13:22
 */
@Slf4j
@SpringBootTest(classes = TmdrkSpringbootAceadminApplication.class)
@RunWith(SpringRunner.class)
public class TransactionPropagationTest {
    @Resource
    private TransactionService transactionService;

    @Test
    public void testRequire() {
        MarketingGameDetail detail = new MarketingGameDetail();
        detail.setId(1L);detail.setAttrDesc("活动奖励2");
        OmpAddress ompAddress = new OmpAddress();
        ompAddress.setId(1L);ompAddress.setShortName("山东2");
        Area area = new Area();
        area.setId(110000);area.setShortname("北京2");
        transactionService.testRequire(detail,ompAddress,area);
    }
}
