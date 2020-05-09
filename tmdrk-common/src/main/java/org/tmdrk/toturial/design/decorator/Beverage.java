package org.tmdrk.toturial.design.decorator;

/**
 * @ClassName Beverage
 * @Description 饮料抽象类
 * @Author zhoujie
 * @Date 2020/2/14 9:49
 * @Version 1.0
 **/
public abstract class Beverage {
    String description;
    String getDescription(){
        return description;
    }
    abstract double cost();
}
