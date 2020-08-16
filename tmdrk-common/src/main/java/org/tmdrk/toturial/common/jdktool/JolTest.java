package org.tmdrk.toturial.common.jdktool;

import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.openjdk.jol.info.ClassLayout;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName JolTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/6/2 0:24
 * @Version 1.0
 **/
public class JolTest {

    public static void main(String[] args) {
        Object[] objs = new Object[10];
        System.out.println(ClassLayout.parseInstance(objs).toPrintable());
        int[] ints = new int[10];
        System.out.println(ClassLayout.parseInstance(ints).toPrintable());
        long[] longs = new long[10];
        System.out.println(ClassLayout.parseInstance(longs).toPrintable());
        Object obj = new Object();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj){
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }
        User user = new User();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());

        Date date = new Date();
        System.out.println(ClassLayout.parseInstance(date).toPrintable());
        BigDecimal bigDec1 = new BigDecimal(2);
        System.out.println(ClassLayout.parseInstance(bigDec1).toPrintable());
        BigDecimal bigDec2 = new BigDecimal("22222222222222222222222222222");
        System.out.println(ClassLayout.parseInstance(bigDec2).toPrintable());
        int a1 = 2;
        System.out.println("int 占用的字节数:"+ClassLayout.parseInstance(a1).toPrintable());
        Integer a2 = new Integer(2);
        System.out.println(ClassLayout.parseInstance(a2).toPrintable());

        Dog dog = new Dog();
        System.out.println(ClassLayout.parseInstance(dog).toPrintable());
        System.out.println(ClassLayout.parseInstance(dog.aa).toPrintable());
        Boolean b = false;
        System.out.println(ClassLayout.parseInstance(b).toPrintable());

        VolatileLong vo = new VolatileLong();
        System.out.println(ClassLayout.parseInstance(vo).toPrintable());
    }

}
class User {
    int a = 10;
    int b = 3;
    Cat cat = new Cat();

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
class Cat {
    int b = 23;
}
class Dog {
    int b = 23;
    int[] aa = new int[10];
}

class VolatileLong {

    public volatile long value = 0L;

    public long p1, p2, p3, p4, p5; // 注释

    public void getLong(){
        long aa = p1+p2+p3+p4+p5;
    }

}