package com.tmdrk.ace.admin.util;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 事务工具类
 *
 * @author lee
 */
public class TransactionUtil {
    public static TransactionStatusWrapper getTransactionStatus(PlatformTransactionManager txManager) {
        return new TransactionStatusWrapper(txManager);
    }

    public static TransactionStatusWrapper getTransactionStatus(PlatformTransactionManager txManager, int isolationLevel) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(isolationLevel);
        return new TransactionStatusWrapper(txManager, def);
    }

    public static TransactionStatusWrapper getTransactionStatus(PlatformTransactionManager txManager, int propagationBehavior, int isolationLevel) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(propagationBehavior);
        def.setIsolationLevel(isolationLevel);
        return new TransactionStatusWrapper(txManager, def);
    }

}
