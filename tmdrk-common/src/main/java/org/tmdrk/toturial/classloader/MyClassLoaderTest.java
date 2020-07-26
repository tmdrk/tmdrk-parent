package org.tmdrk.toturial.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName MyClassLoaderTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/3 17:48
 * @Version 1.0
 **/
public class MyClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    try {
//                        String name = Thread.currentThread().getName()+" ";
//                        Person p = new Person(3);
//                        p.sayHello();
//                        System.out.println(name+p.getClass().getClassLoader()+p);
//                        System.out.println(name+p.getCat().getClass().getClassLoader());
//                        Thread.sleep(2000) ;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        }).start();

        int i = 0 ;
        String name = Thread.currentThread().getName()+" ";
        Person p = new Person(3);
        p.sayHello();
        System.out.println(name+p.getClass().getClassLoader()+p);

        MyClassLoader myClassLoader = new MyClassLoader() ;
        //抛异常：java.lang.SecurityException: Prohibited package name: java.lang
        Class<?> clazz =  myClassLoader.findClass("java.lang.String111");
        while(true){
            MyClassLoader mcl = new MyClassLoader() ;
            System.out.println(name+mcl.getParent());
            Class<?> personClass =  mcl.findClass("org.tmdrk.toturial.classloader.Person");

            try {
                Object person =  personClass.newInstance() ;
                Cat cat = Cat.class.newInstance();
                if(person instanceof Person){
                    System.out.println("person is true");
                }else{
                    System.out.println("person is false");
                }
                Method sayHelloMethod = personClass.getMethod("sayHello") ;
                Method getCat = personClass.getMethod("getCat") ;
                sayHelloMethod.invoke(person) ;
                getCat.invoke(person) ;
                System.out.println(name+person.getClass().getClassLoader()+person);
                System.out.println(name+(++i));

            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(2000) ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
