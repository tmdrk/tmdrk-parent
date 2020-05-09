package org.tmdrk.toturial.design.singleton;

/**
 * @ClassName Singleton1
 * @Description
 * 当getInstance方法第一次被调用的时候, 它第一次读取SingletonHolder.instance，导致SingletonHolder类得到初始化；
 * 而这个类在装载并被初始化的时候，会初始化它的静态域，从而创建Singleton的实例，由于是静态的域，因此只会在虚拟机装载类的
 * 时候初始化一次，并由虚拟机来保证它的线程安全性。
 * @Author zhoujie
 * @Date 2020/2/16 11:49
 * @Version 1.0
 **/
public class Singleton1 {
    private static class SingletonHoler {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static Singleton1 singleton1 = new Singleton1();
    }

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return SingletonHoler.singleton1;
    }
}
