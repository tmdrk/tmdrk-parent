package com.tmdrk.ace.admin.service;

import com.tmdrk.ace.admin.TmdrkSpringbootAceadminApplication;
import com.tmdrk.ace.admin.entity.MarketingGameDetail;
import com.tmdrk.ace.admin.util.TransactionStatusWrapper;
import com.tmdrk.ace.admin.util.TransactionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;

/**
 * TransactionTest
 *
 * @author Jie.Zhou
 * @date 2021/2/25 13:12
 */
@Slf4j
@SpringBootTest(classes = TmdrkSpringbootAceadminApplication.class)
@RunWith(SpringRunner.class)
public class TransactionTest {
    @Resource
    private MarketingGameDetailService marketingGameDetailService;

    //事务模板
    @Autowired
    private TransactionTemplate transactionTemplate;

    //自定义
    @Resource
    private PlatformTransactionManager txManager;

    //使用DataSourceTransactionManager
    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    TransactionDefinition transactionDefinition;

    @Test
    public void savePoint() {
        marketingGameDetailService.testSavePoint();
    }

    @Test
    public void dataSourceTransactionManager() {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try{
            //数据操作
            dataSourceTransactionManager.commit(transactionStatus);//提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);
            //手动业务回滚逻辑
        }
    }

    //自定义工具类
    @Test
    public void txManager() {
        TransactionStatusWrapper tx = TransactionUtil.getTransactionStatus(txManager);
        try{
            //数据操作
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            //手动业务回滚逻辑
        }
    }

    @Test
    public void transactionTemplateTest() {
        //测试手动事务
//		transactionTemplate.afterPropertiesSet();
        MarketingGameDetail insert = new MarketingGameDetail();
        insert.setGameId(1L);
        insert.setAttrValue("100");
        insert.setAttrName("101");
        insert.setAttrDesc("test");
        insert.setCreateTime(new Date());
        insert.setIsDel(false);
        MarketingGameDetail update = new MarketingGameDetail();
        update.setId(18L);
        update.setAttrValue("2020-09-11 12:22:22");
        Object execute = transactionTemplate.execute(new TransactionCallback() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                marketingGameDetailService.insert(insert);
//				int a = 1/0;
                marketingGameDetailService.update(update);
                return Boolean.TRUE;
            }
        });
        System.out.println("==================================");
    }
}
