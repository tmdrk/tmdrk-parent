package org.tmdrk.toturial.design.strategy;

/**
 * @ClassName PayGD
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/13 13:25
 * @Version 1.0
 **/
public class PayGD extends Pay{
    public PayGD(){
        this(new PaymentGD(),new InvestigateGD(),new ReconciliationGD());
    }
    public PayGD(Payment payment,Investigate investigate,Reconciliation reconciliation){
        this.payment = payment;
        this.investigate = investigate;
        this.reconciliation = reconciliation;
    }

    public void pay(){
        if(payment == null){
            System.out.println("payment can not null");
            return;
        }
        payment.pay();
    }

    public void invest(){
        if(investigate == null){
            System.out.println("investigate can not null");
            return;
        }
        investigate.invest();
    }
}
