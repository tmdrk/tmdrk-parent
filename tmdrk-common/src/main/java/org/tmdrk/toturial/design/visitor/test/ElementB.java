package org.tmdrk.toturial.design.visitor.test;

/**
 * @ClassName ElementB
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/11 19:28
 * @Version 1.0
 **/
public class ElementB implements Element{
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
