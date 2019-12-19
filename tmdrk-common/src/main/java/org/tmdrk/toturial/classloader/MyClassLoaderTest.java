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

        int i = 0 ;

        while(true){
            MyClassLoader mcl = new MyClassLoader() ;
            System.out.println(mcl.getParent());
            Class<?> personClass =  mcl.findClass("org.tmdrk.toturial.classloader.Person");

            try {
                Object person =  personClass.newInstance() ;
                Method sayHelloMethod = personClass.getMethod("sayHello") ;
                sayHelloMethod.invoke(person) ;
                System.out.println(++i);
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
