package org.tmdrk.toturial.design.decorator;

/**
 * @ClassName Sugar
 * @Description 糖
 * @Author zhoujie
 * @Date 2020/2/14 11:25
 * @Version 1.0
 **/
public class Sugar extends CondimentDecorator{
    Beverage beverage;
    double cost = 1.0;
    public Sugar(Beverage beverage){
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return "糖 "+beverage.getDescription();
    }

    @Override
    double cost() {
        return cost+beverage.cost();
    }
}
