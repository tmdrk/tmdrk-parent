package org.tmdrk.toturial.design.decorator;

/**
 * @ClassName HouseBlend
 * @Description 综合(巴克的HOUSE BLEND 叫首选咖啡.顺滑/清爽的口味)
 * @Author zhoujie
 * @Date 2020/2/14 9:52
 * @Version 1.0
 **/
public class HouseBlend extends Beverage {
    public HouseBlend(){
        description = "DarkRoast";
    }
    @Override
    double cost() {
        return 5.0;
    }
}
