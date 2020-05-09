package org.tmdrk.toturial.design.strategy;

/**
 * @ClassName PaymentJD
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/13 13:22
 * @Version 1.0
 **/
public class PaymentJD implements Payment{
    @Override
    public void pay() {
        System.out.println("京东支付...");
    }
}
