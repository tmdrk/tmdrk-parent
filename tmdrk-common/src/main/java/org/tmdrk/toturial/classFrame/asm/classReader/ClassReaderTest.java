package org.tmdrk.toturial.classFrame.asm.classReader;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.tmdrk.toturial.classFrame.asm.classVisitor.AddTimerAdapter;
import org.tmdrk.toturial.classFrame.asm.classVisitor.ClassPointer;
import org.tmdrk.toturial.classFrame.asm.classWriter.MyClassLoader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName ClassReaderTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/6 20:03
 * @Version 1.0
 **/
public class ClassReaderTest extends ClassLoader{
    public static void main(String[] args) throws Exception {
//        doClassPointer();
        doAddTimerAdapter();
    }

    /**
     * @Author zhoujie
     * @Description 模拟javap操作，打印类相关信息
     * @Date 17:12 2020/1/8
     * @Param []
     * @return void
     **/
    public static void doClassPointer() throws IOException {
        ClassPointer cp = new ClassPointer(262144);//继承Classvisitor可以重写解析过程
        ClassReader cr = new ClassReader("java.lang.Runnable");
        cr.accept(cp,0);

        // 使用全限定名，创建一个ClassReader对象
        ClassReader classReader = new ClassReader("org.tmdrk.toturial.classFrame.asm.classReader.Person");
        classReader.accept(cp,0);
    }

    /**
     * @Author zhoujie
     * @Description 给类动态增加调用时间统计。
     * @Date 17:13 2020/1/8
     * @Param []
     * @return void
     **/
    public static void doAddTimerAdapter() throws Exception {
        ClassWriter cw = new ClassWriter(0);
        ClassReader cr = new ClassReader("org.tmdrk.toturial.classFrame.asm.Test1");
        AddTimerAdapter addTimerAdapter = new AddTimerAdapter(cw);
        cr.accept(addTimerAdapter,0);
        byte[] data = cw.toByteArray();
        MyClassLoader mcl = new MyClassLoader();
        Class aClass = mcl.defineClass(null, data);
        Object o = aClass.newInstance();
        //通过反射调用main方法
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field:declaredFields){
            field.setAccessible(true);
            System.out.println(field.getName()+"="+field.get(o));
        }
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method:declaredMethods){
            System.out.println(method.getName());
        }
        aClass.getDeclaredMethods()[0].invoke(o, new Object[] { });
        for (Field field:declaredFields){
            field.setAccessible(true);
            System.out.println(field.getName()+"="+field.get(o));
        }
    }
}
