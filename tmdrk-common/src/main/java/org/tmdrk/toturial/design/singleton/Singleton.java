package org.tmdrk.toturial.design.singleton;

/**
 * @ClassName Singleton
 * @Description 双重检查锁
 * @Author zhoujie
 * @Date 2020/2/16 11:36
 * @Version 1.0
 **/
public class Singleton {
    private volatile static Singleton singleton;
    private Singleton(){

    }
    public static Singleton getSingleton(){
        if(singleton==null){
            synchronized (Singleton.class){
                if(singleton==null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
