package org.tmdrk.toturial.classFrame.asm.classVisitor;

import org.objectweb.asm.*;

/**
 * @ClassName ClassPointer
 * @Description 类适配器，简单模仿javap指令
 * @Author zhoujie
 * @Date 2020/1/6 20:08
 * @Version 1.0
 **/
    public class ClassPointer extends ClassVisitor {
    public ClassPointer(int i) {
        super(i);
    }
    @Override
    public void visit(int i, int i1, String s, String s1, String s2, String[] strings) {
        System.out.println(s+" extends "+s2+"{");
    }
    @Override
    public void visitSource(String s, String s1) {
    }
    @Override
    public void visitOuterClass(String s, String s1, String s2) {
    }
    @Override
    public AnnotationVisitor visitAnnotation(String s, boolean b) {
        return null;
    }
    @Override
    public void visitAttribute(Attribute attribute) {
    }
    @Override
    public void visitInnerClass(String s, String s1, String s2, int i) {
    }
    @Override
    public FieldVisitor visitField(int i, String s, String s1, String s2, Object o) {
        System.out.println(" "+s1+" "+s);
        return null;
    }
    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        System.out.println(" "+s+s1);
        return null;
    }
    @Override
    public void visitEnd() {
        System.out.println("}");
    }
}
