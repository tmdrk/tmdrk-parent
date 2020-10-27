package org.tmdrk.toturial.collection.list;

import java.util.LinkedList;

/**
 * LinkedListTest
 * 测试删除最先/最后添加的元素
 * @author Jie.Zhou
 * @date 2020/10/26 11:05
 */
public class LinkedListTest {
    public static void main(String[] args) {
        LinkedList ll =new LinkedList();
        ll.add("yang");
        ll.add("zhang");
        ll.add("wang");
        for(int i = 0;i<ll.size();i++)
            System.out.println(ll.get(i));
        System.out.println("=============");
        ll.removeLast();
        ll.removeFirst();
        for(int i = 0;i<ll.size();i++)
            System.out.println(ll.get(i));
        System.out.println("=============");
    }
}
