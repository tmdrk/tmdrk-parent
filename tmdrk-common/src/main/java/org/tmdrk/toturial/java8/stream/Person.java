package org.tmdrk.toturial.java8.stream;

/**
 * @ClassName Person
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/2/21 17:40
 * @Version 1.0
 **/
public class Person {
    private long id;
    private int age;
    private String name;

    public Person(long id,int age,String name){
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
