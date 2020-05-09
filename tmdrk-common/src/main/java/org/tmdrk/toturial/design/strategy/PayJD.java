package org.tmdrk.toturial.design.strategy;

/**
 * @ClassName PayJD
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/13 13:24
 * @Version 1.0
 **/
public class PayJD extends Pay{
    public PayJD(){
        this(new PaymentJD(),new InvestigateJD(),new ReconciliationJD());
    }
    public PayJD(Payment payment,Investigate investigate,Reconciliation reconciliation){
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

    public void reconciliation(){
        if(reconciliation == null){
            System.out.println("reconciliation can not null");
            return;
        }
        reconciliation.reconciliation();
    }
}
