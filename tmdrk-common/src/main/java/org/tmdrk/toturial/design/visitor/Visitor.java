package org.tmdrk.toturial.design.visitor;

/**
 * @ClassName Visitor
 * @Description
 * 访问者模式的使用场景
 * 1.对象结构比较稳定，但经常需要在此对象结构上定义新的操作。
 * 2.需要对一个对象结构中的对象进行很多不同的并且不相关的操作，而需要避免这些操作“污染”这些对象的类，也不希望在增加新操作时修改这些类。
 *
 * @Author zhoujie
 * @Date 2020/2/11 18:57
 * @Version 1.0
 **/
public interface Visitor {
    void visit(long a);
    void visit(int a);
}
