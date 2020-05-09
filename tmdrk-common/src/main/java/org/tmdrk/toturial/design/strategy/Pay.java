package org.tmdrk.toturial.design.strategy;

/**
 * @ClassName Pay
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/13 13:14
 * @Version 1.0
 **/
public abstract class Pay {
    Payment payment;
    Investigate investigate;
    Reconciliation reconciliation;

    public void pay(){}

    public void invest(){}

    public void reconciliation(){}


    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Investigate getInvestigate() {
        return investigate;
    }

    public void setInvestigate(Investigate investigate) {
        this.investigate = investigate;
    }

    public Reconciliation getReconciliation() {
        return reconciliation;
    }

    public void setReconciliation(Reconciliation reconciliation) {
        this.reconciliation = reconciliation;
    }
}
