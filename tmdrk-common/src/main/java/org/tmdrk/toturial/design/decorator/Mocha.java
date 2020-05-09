package org.tmdrk.toturial.design.decorator;

/**
 * @ClassName Mocha
 * @Description 摩卡
 * @Author zhoujie
 * @Date 2020/2/14 11:12
 * @Version 1.0
 **/
public class Mocha extends CondimentDecorator{
    Beverage beverage;
    double cost = 1.2;

    public Mocha(Beverage beverage){
        this.beverage = beverage;
    }
    @Override
    public String getDescription() {
        return "摩卡 "+beverage.getDescription();
    }

    @Override
    double cost() {
        return cost+beverage.cost();
    }
}
