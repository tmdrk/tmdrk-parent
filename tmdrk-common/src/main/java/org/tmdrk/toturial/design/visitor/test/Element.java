package org.tmdrk.toturial.design.visitor.test;

/**
 * @ClassName Element
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/11 19:26
 * @Version 1.0
 **/
public interface Element {
    void accept(Visitor visitor);
}
