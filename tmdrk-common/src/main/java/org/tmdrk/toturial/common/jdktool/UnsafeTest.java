package org.tmdrk.toturial.common.jdktool;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @ClassName UnsafeTest
 * @Description 反射使用Unsafe类
 * @Author zhoujie
 * @Date 2020/6/11 2:45
 * @Version 1.0
 **/
public class UnsafeTest {
    public static Unsafe getUnsafeInstance() {
        try {
            Class<?> clazz = Unsafe.class;
            Field f = clazz.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(clazz);
            return unsafe;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getUnsafeInstance());
    }
}
