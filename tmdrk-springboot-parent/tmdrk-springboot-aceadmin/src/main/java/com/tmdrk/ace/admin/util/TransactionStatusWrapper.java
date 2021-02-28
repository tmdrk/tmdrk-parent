package com.tmdrk.ace.admin.util;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 事务状态包装
 *
 * @author lee
 */
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
    }

    public void rollback() {
        txManager.rollback(tx);
    }
}
