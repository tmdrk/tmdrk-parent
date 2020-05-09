package org.tmdrk.toturial.collection.map;

import org.apache.directory.api.util.SynchronizedLRUMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ConcurrentHashMapTest
 * @Description
 * @Author zhoujie
 * @Date 2020/3/23 16:43
 * @Version 1.0
 **/
public class ConcurrentHashMapTest {

    static final int HASH_BITS = 0x7fffffff;
    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        LinkedHashMap<String,Integer> linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("aa",12);
        linkedHashMap.put("cc",11);
        linkedHashMap.put("bb",25);
        linkedHashMap.put("ab",9);
        linkedHashMap.put("aa",16);
        linkedHashMap.put("ae",17);
        System.out.println(linkedHashMap.size());
        Iterator iterator = linkedHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next);
        }
        linkedHashMap.forEach((k,v)-> System.out.println(k+":"+v));

        LinkedList l = new LinkedList();
        l.add(1);
        Test t =  new Test(22);
        Test t1 = (Test)t.clone();
        t1.setSum(33);
        System.out.println(t+"|"+t1);
        Test[] aa = new Test[5];
        for (int i=0;i<5;i++){
            aa[i] =new Test(i);
        }
        Test[] bb = new Test[5];
        System.arraycopy(aa,0,bb,0,4);
        Arrays.stream(bb).forEach(a-> System.out.println(a));
        bb[1].setSum(77);
        Arrays.stream(aa).forEach(a-> System.out.println(a));

        ArrayList arrayList = new ArrayList();
//        getConflict(16);

//        hashConflictResize();

        normalResize();

//        addCountTest();
    }
    public static void getConflict(int tableSize){
        List list = new ArrayList();
        for(int i=0;i<2000;i++){
            int hash = spread(("key_" + i).hashCode());
            if(((tableSize-1)&hash)==1){
                list.add(i);
            }
        }
        System.out.println(list);
    }

    public static void hashConflictResize(){
//        List<Integer> list = Arrays.asList(2, 10, 21, 32, 43, 54, 65, 76, 87, 98, 107, 118, 129, 190);
        List<Integer> list = Arrays.asList(2, 10, 21, 87, 98, 107, 118, 129, 190,76);
        ConcurrentHashMap c = new ConcurrentHashMap();
        c.put("key_" + 1,1);
        for (Integer i:list) {
            c.put("key_" + i,i);
        }
    }

    public static void normalResize(){
        ConcurrentHashMap c = new ConcurrentHashMap();
        new Thread(new Runnable() {
            @Override
            public void run() {
                c.put("key_" + 1002,1002);
            }
        }).start();
        for(int i=0;i<100;i++){ //96时会扩容成128
            c.put("key_" + i,i);
        }
        System.out.println(c.get("key_" + 1002));
    }

    public static void getSize(){
        ConcurrentHashMap c = new ConcurrentHashMap();
        for(int i=0;i<100;i++){ //96时会扩容成128
            c.put("key_" + i,i);
        }
        c.size();
        c.remove(1);
    }

    public static void addCountTest(){
        ConcurrentHashMap c = new ConcurrentHashMap();
        new Thread(new Runnable() {
            @Override
            public void run() {
                c.put("key_" + 2,2);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                c.put("key_" + 10,10);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                c.put("key_" + 12,12);
            }
        }).start();
    }

}
