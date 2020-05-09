package org.tmdrk.toturial.design.visitor.test;

/**
 * @ClassName Test
 * @Description 访问者模式适用于对象结构（Element）稳定，访问者可能变化的情形
 * @Author zhoujie
 * @Date 2020/2/11 19:29
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) {
        Visitor visitor = new VisitorA();
        Element element = new ElementA();
        element.accept(visitor);
    }
}
