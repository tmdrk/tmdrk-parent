package org.tmdrk.toturial.classFrame.asm.classWriter;

/**
 * @ClassName MyClassLoader
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/7 8:45
 * @Version 1.0
 **/
public class MyClassLoader extends ClassLoader{
    public Class defineClass(String name, byte[] b){
        return defineClass(name, b, 0, b.length);
    }
}
