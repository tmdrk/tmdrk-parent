package org.tmdrk.toturial.classloader;

import com.baibo.web.util.TestPerson;

import java.lang.reflect.Field;

/**
 * @ClassName Person
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/3 17:48
 * @Version 1.0
 **/
public class Person {
    Cat cat = new Cat(2);
    int a = 0;
    public Person(){
    }
    public Person(int a){
        this.a = a;
    }
    public int sayHello(){
//        System.color.println("hello world！");
        System.out.println(Thread.currentThread()+"we are the loser");
        return 0;
    }

    public static void main(String[] args) throws ClassNotFoundException {
//        TestPerson person = new TestPerson();
        Class<?> aClass = Class.forName("com.baibo.web.util.TestPerson");
        System.out.println(aClass.getClassLoader());
        Field[] declaredFields = aClass.getDeclaredFields();
        for(Field field:declaredFields){
            System.out.println(Thread.currentThread().getName()+" "+field.getName());
        }
    }

    public Cat getCat() {
        System.out.println("==================="+cat.getClass().getClassLoader());
        return cat;
    }

    @Override
    public String toString() {
        return "Person{" +
                "a=" + a +
                '}';
    }
}
