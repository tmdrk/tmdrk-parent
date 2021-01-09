package org.tmdrk.toturial.collection.list;

import java.util.ArrayList;
import java.util.List;

/**
 * ListLongTest
 *
 * @author Jie.Zhou
 * @date 2020/12/21 18:01
 */
public class ListLongTest {
    public static void main(String[] args) {
        List<Long> list = null;
//        getTest(list);
//        System.out.println(list.get(0));

        for(int i=1;i<100;i++){
            System.out.println(1 + (i + 1) * i / 2);
        }
    }

    private static void getTest(List<Long> list) {
        getHome(list);
    }

    private static void getHome(List<Long> list) {
        list.addAll(newList());
    }

    private static List<Long> newList() {
        ArrayList<Long> objects = new ArrayList<>();
        objects.add(1L);
        return objects;
    }
}
