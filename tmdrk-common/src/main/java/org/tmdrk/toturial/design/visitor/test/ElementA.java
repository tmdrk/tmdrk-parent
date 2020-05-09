package org.tmdrk.toturial.design.visitor.test;

/**
 * @ClassName ElementA
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/11 19:27
 * @Version 1.0
 **/
public class ElementA implements Element{
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
