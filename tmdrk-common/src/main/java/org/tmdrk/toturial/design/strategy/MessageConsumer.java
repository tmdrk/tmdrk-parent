package org.tmdrk.toturial.design.strategy;

/**
 * @ClassName MessageConsumer
 * @Description
 * @Author zhoujie
 * @Date 2020/2/13 14:05
 * @Version 1.0
 **/
public class MessageConsumer {
    public static void main(String[] args) {
        Pay pay = new PayGD();
        pay.pay();
        pay.invest();
        pay.reconciliation();

        pay = new PayJD();
        pay.pay();
        pay.invest();
        pay.reconciliation();

        pay.setPayment(new PaymentGD());
        pay.pay();
    }
}
