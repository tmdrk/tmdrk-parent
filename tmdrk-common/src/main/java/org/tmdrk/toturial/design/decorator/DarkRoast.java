package org.tmdrk.toturial.design.decorator;

/**
 * @ClassName DarkRoast
 * @Description 深度烘培
 * @Author zhoujie
 * @Date 2020/2/14 10:54
 * @Version 1.0
 **/
public class DarkRoast extends Beverage {
    public DarkRoast(){
        description = "DarkRoast";
    }
    @Override
    double cost() {
        return 5.5;
    }
}
