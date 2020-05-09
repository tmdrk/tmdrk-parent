package org.tmdrk.toturial.design.decorator;

/**
 * @ClassName Decaf
 * @Description 低咖(脱咖啡因咖啡)
 * @Author zhoujie
 * @Date 2020/2/14 10:55
 * @Version 1.0
 **/
public class Decaf extends Beverage {
    public Decaf(){
        description = "DarkRoast";
    }
    @Override
    double cost() {
        return 6.0;
    }
}
