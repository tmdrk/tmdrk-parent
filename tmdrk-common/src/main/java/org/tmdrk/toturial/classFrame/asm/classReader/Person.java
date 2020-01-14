package org.tmdrk.toturial.classFrame.asm.classReader;

import org.objectweb.asm.ClassVisitor;

/**
 * @ClassName Person
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/6 20:31
 * @Version 1.0
 **/
public class Person extends ClassVisitor {
    private String name;

    public Person(int i) {
        super(i);
    }

    public void sayName() {
        System.out.println(name);
    }
}
