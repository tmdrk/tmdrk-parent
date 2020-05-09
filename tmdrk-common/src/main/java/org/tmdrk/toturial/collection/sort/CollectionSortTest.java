package org.tmdrk.toturial.collection.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName CollectionSortTest
 * @Description
 * 1.对于集合比较使用Collections.sort();
 * 2.对于集合中的对象比较，需要指定比较逻辑，指定比较逻辑需要对象实现Comparable接口并重写compareTo方法自定义逻辑。
 * 3.对于需要临时改变比较规则，需要使用Collections.sort(List,Comparator),采用回调方式重写Comparator接口的compare方法自定义逻辑。
 * @Author zhoujie
 * @Date 2020/1/9 22:18
 * @Version 1.0
 **/
public class CollectionSortTest {
    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        list.add(new User(3,"aa"));
        list.add(new User(6,"bb"));
        list.add(new User(4,"cc"));
        list.add(new User(1,"dd"));
        Collections.sort(list);

        list.stream().forEach(user -> System.out.println(user.getAge()));

        List<User> list1 = new ArrayList<>();
        list1.add(new User(3,"aa"));
        list1.add(new User(6,"bb"));
        list1.add(new User(4,"cc"));
        list1.add(new User(1,"dd"));

        Collections.sort(list1, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.age - o2.age;
            }
        });
        list1.stream().forEach(user -> System.out.println(user.getAge()));

        List<User> list2 = new ArrayList<>();
        list2.add(new User(3,"aa"));
        list2.add(new User(6,"bb"));
        list2.add(new User(4,"cc"));
        list2.add(new User(1,"dd"));

        list2.stream().sorted((a, b) -> b.getAge() - (a.getAge())).forEach(user -> System.out.println(user.getAge()));
    }
}
class User implements Comparable<User>{
    int age;
    String name;

    public User(int age,String name){
        this.age = age;
        this.name = name;
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

    @Override
    public int compareTo(User o) {
        return this.age - o.age;
    }
}