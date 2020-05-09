package org.tmdrk.toturial.design.visitor.test;

/**
 * @ClassName VisitorA
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/11 19:25
 * @Version 1.0
 **/
public class VisitorA implements Visitor{
    @Override
    public void visit(ElementA a) {
        System.out.println("visit a");
    }

    @Override
    public void visit(ElementB a) {
        System.out.println("visit b");
    }
}
