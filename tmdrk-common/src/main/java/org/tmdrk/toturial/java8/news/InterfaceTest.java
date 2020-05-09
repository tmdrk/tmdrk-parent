package org.tmdrk.toturial.java8.news;

/**
 * @ClassName InterfaceTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/4/10 15:04
 * @Version 1.0
 **/
public interface InterfaceTest {
    default String preName(String name){
        return "pre-"+name;
    }
    default String getName(String name){
        return sufName(preName(name.toUpperCase()));
    }
    default String sufName(String name){
        return name+"-suf";
    }
}
