package org.tmdrk.toturial.design.decorator;

/**
 * @ClassName OrderTest
 * @Description 动态的将责任添加到对象上
 * @Author zhoujie
 * @Date 2020/2/14 11:28
 * @Version 1.0
 **/
public class OrderTest {
    public static void main(String[] args) {
        DarkRoast darkRoast = new DarkRoast();
        Milk milk = new Milk(darkRoast);
        Sugar sugar = new Sugar(milk);
        Mocha mocha = new Mocha(sugar);
        mocha = new Mocha(mocha);
        System.out.println("名称："+mocha.getDescription()+" 总价格：￥"+mocha.cost());
    }
}
