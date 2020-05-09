package org.tmdrk.toturial.design.visitor.test;

/**
 * @ClassName Visitor
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/11 19:24
 * @Version 1.0
 **/
public interface Visitor {
    void visit(ElementA a);
    void visit(ElementB a);
}
