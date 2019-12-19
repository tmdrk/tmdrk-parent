package org.tmdrk.toturial.collection.list;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName ListRemove
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/10/14 20:23
 * @Version 1.0
 **/
public class ListRemove {
    public static void main(String[] args) {
        Random random = new Random();
        List<Integer> tmp = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
//        CopyOnWriteArrayList list = new CopyOnWriteArrayList(tmp);
        LinkedList list1 = new LinkedList(tmp);
        ArrayList list = new ArrayList(list1);
        while(list.size()!=0){
            int index = random.nextInt(list.size());
            System.out.println("index="+index+" vlaue="+list.get(index)+" size="+list.size());
            list.remove(index);
        }

    }
}
