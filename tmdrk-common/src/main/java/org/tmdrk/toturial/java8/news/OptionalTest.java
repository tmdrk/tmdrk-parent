package org.tmdrk.toturial.java8.news;

import lombok.Data;

import java.util.Optional;

/**
 * OptionalTest
 *
 * @author Jie.Zhou
 * @date 2020/12/30 11:41
 */
public class OptionalTest {
    public static void main(String[] args) {
        Human human = new Human();
//        People people = new People();
//        people.setUserName("zhou");
        Man man = new Man();
        man.setAge("12");
//        human.setPeople(people);
//        String name = Optional.ofNullable(Optional.ofNullable(human.getPeople())).orElse("default");
//        System.out.println(name);
        int aa = 200;
        System.out.println(aa==200);
        Integer bb = 200;
        System.out.println(bb==200);
    }
}
@Data
class Human{
    private People people;
}
@Data
class People{
    private String userName;
    private Man Man;
}
@Data
class Man{
    private String age;
}