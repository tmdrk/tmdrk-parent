package org.tmdrk.toturial.classFrame.asm.classWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.tmdrk.toturial.classFrame.asm.classVisitor.AddTimerAdapter;
import java.io.IOException;

/**
 * @ClassName StubClassLoader
 * @Description 对于_stub结尾的类统计，统一加上方法调用时间统计
 * @Author zhoujie
 * @Date 2020/1/7 8:56
 * @Version 1.0
 **/
public class StubClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if(name.endsWith("_stub")) {
            ClassWriter cw = new ClassWriter(0);
            ClassReader cr = null;
            try {
                cr = new ClassReader(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            AddTimerAdapter addTimerAdapter = new AddTimerAdapter(cw);
            cr.accept(addTimerAdapter,0);
            byte[] data = cw.toByteArray();

            return defineClass(name, data, 0, data.length);
        }
        return super.findClass(name);
    }
}
