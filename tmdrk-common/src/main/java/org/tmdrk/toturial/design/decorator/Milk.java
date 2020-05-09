package org.tmdrk.toturial.design.decorator;

/**
 * @ClassName Milk
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/14 11:23
 * @Version 1.0
 **/
public class Milk extends CondimentDecorator{
    Beverage beverage;
    double cost = 2.0;
    public Milk(Beverage beverage){
        this.beverage = beverage;
    }
    @Override
    public String getDescription() {
        return "牛奶 "+beverage.getDescription();
    }

    @Override
    double cost() {
        return cost+beverage.cost();
    }
}
