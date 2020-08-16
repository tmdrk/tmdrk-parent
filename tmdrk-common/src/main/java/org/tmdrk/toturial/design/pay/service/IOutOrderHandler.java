package org.tmdrk.toturial.design.pay.service;

/**
 * @ClassName IOutOrderHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/8/16 23:52
 * @Version 1.0
 **/
public interface IOutOrderHandler<T> {
    default T submitOrder(){
        checkOrder();
        postProcessBeforeOrder();
        doSaveOrder();
        T result = doSubmitOrder();
        postProcessAfterOrder();
        return result;
    }
    void checkOrder();
    void postProcessBeforeOrder();
    void doSaveOrder();
    <T> T doSubmitOrder();
    void postProcessAfterOrder();
}
