package org.tmdrk.toturial.collection.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName LruMap
 * @Description 使用linkedHashMap实现LRU最近最少使用算法
 * @Author zhoujie
 * @Date 2020/1/5 10:59
 * @Version 1.0
 **/
public class LruMap<K,V> extends LinkedHashMap<K,V>{
    static final int DEFAULT_CAPACITY = 16;
    /**
     * 集合容量，超过此值则会按最近最少使用原则淘汰数据。
     **/
    int capacity;

    public LruMap(){
        this(DEFAULT_CAPACITY);
    }
    public LruMap(int capacity){
        this.capacity = capacity;
    }

    public static void main(String[] args) {
        printHashMap();
        System.out.println("=================================================");
        printLinkedHashMap();

    }

    public static void printHashMap(){
        Map hashMap = new HashMap();
        for (int i=0;i<10;i++){
            hashMap.put("key_"+i,i);
        }
        hashMap.forEach((key,value)->{
            System.out.println("key="+key+" value="+value);
        });
    }

    public static void printLinkedHashMap(){
        LruMap lruMap = new LruMap(10);
        for (int i=0;i<15;i++){
            lruMap.lruPut("key_"+i,i);
        }
        lruMap.lruPut("key_8",16);
        lruMap.forEach((key,value)->{
            System.out.println("key="+key+" value="+value);
        });
        System.out.println("=================================================");

        lruMap.lruGet("key_2");
        lruMap.lruPut("key_5",5*2);
        lruMap.lruPut("key_20",20);
//        lruMap.lruRemove("key_8");

        lruMap.forEach((key,value)->{
            System.out.println("key="+key+" value="+value);
        });

    }

    public V lruGet(K key){
        if(this.containsKey(key)){
            V value = this.get(key);
            this.remove(key);
            this.put(key,value);
            return value;
        }
        return null;
    }

    public V lruPut(K key,V value){
        boolean flag = true;
        if(this.containsKey(key)){
            this.remove(key);
            flag = false;
        }
        if(flag && this.size() >= capacity){
            K next = this.keySet().iterator().next();
            this.lruRemove(next);
        }
        return this.put(key,value);
    }

    public V lruRemove(Object key){
        return this.remove(key);
    }
}
