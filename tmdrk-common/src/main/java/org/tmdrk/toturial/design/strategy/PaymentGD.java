package org.tmdrk.toturial.design.strategy;

/**
 * @ClassName PaymentGD
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/13 13:23
 * @Version 1.0
 **/
public class PaymentGD implements Payment{
    @Override
    public void pay() {
        System.out.println("光大支付...");
    }
}
