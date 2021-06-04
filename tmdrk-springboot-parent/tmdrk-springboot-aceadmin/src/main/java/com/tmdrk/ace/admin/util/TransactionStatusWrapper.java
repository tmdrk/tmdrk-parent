package com.tmdrk.ace.admin.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 事务状态包装
 *
 * @author lee
 */
@Slf4j
public class TransactionStatusWrapper {
    private TransactionStatus          tx;
    private PlatformTransactionManager txManager;

    public TransactionStatusWrapper(PlatformTransactionManager txManager) {
        this.tx = txManager.getTransaction(new DefaultTransactionDefinition());
        this.txManager = txManager;
    }

    public TransactionStatusWrapper(PlatformTransactionManager txManager, DefaultTransactionDefinition definition) {
        this.tx = txManager.getTransaction(definition);
        this.txManager = txManager;
    }

    public void commit() {
        txManager.commit(tx);
        log.info("事务提交");
    }

    public void rollback() {
        txManager.rollback(tx);
        log.info("事务回滚");
    }
}
