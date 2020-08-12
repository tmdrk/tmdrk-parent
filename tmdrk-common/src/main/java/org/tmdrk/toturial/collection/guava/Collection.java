package org.tmdrk.toturial.collection.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.tmdrk.toturial.entity.User;
import sun.security.provider.certpath.Vertex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * guava 集合测试
 *
 * @author Jie.Zhou
 * @date 2020/8/11 13:04
 */
public class Collection {
    public static void main(String[] args) throws IOException {
//        testMultiset();
//        testMultimap();
//        testBiMap();
        testTable();

        testMapDiff();

    }

    private static void testMapDiff() {
        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 4, "c", 3,"d",4);
        Map<String, Integer> right = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        Maps.newHashMap();
        MapDifference<String, Integer> diff = Maps.difference(left, right);
        System.out.println(JSON.toJSONString(diff.entriesInCommon())); //
        System.out.println(JSON.toJSONString(diff.entriesOnlyOnLeft())); //
        System.out.println(JSON.toJSONString(diff.entriesOnlyOnRight())); //
        System.out.println(JSON.toJSONString(diff.entriesDiffering())); //
        System.out.println(JSON.toJSONString(diff.areEqual()));
    }

    private static void testTable() {
        //        Table<Vertex, Vertex, Double> weightedGraph = HashBasedTable.create();
//        weightedGraph.put(v1, v2, 4);
//        weightedGraph.put(v1, v3, 20);
//        weightedGraph.put(v2, v3, 5);
//        weightedGraph.row(v1); // returns a Map mapping v2 to 4, v3 to 20
//        weightedGraph.column(v3); // returns a Map mapping v1 to 20, v2 to 5
        Table<Integer, Integer, Integer> table = HashBasedTable.<Integer, Integer, Integer>create();
        table.put(1, 2, 3);
        //允许row和column确定的二维点重复
        table.put(1, 6, 3);
        //判断row和column确定的二维点是否存在
        if(table.contains(1, 2)) {
            table.put(1, 4, 4);
            table.put(2, 5, 4);
            table.put(1,2,1);
            table.put(1,2,0);
        }
        System.out.println(table.get(1,2));
        System.out.println(JSON.toJSONString(table.rowMap()));
        System.out.println(JSON.toJSONString(table.columnMap()));
    }

    private static void testBiMap() {
        BiMap<String, Integer> biMap = HashBiMap.create();
        biMap.put("name",23);
        biMap.put("age",66);
        biMap.put("name",22);
        System.out.println(JSON.toJSONString(biMap));
        BiMap<Integer, String> inverse = biMap.inverse();
//        inverse.put(33,"name");
        inverse.forcePut(33,"name");
        System.out.println(JSON.toJSONString(inverse));
    }

    private static void testMultimap() {
        Multimap<String, Object> multimap = HashMultimap.create();
        multimap.put("name","zhangsan");
        multimap.put("name","lisi");
        multimap.put("name","wangerma");
        multimap.put("age","11");
        multimap.put("age",new User());
        System.out.println(multimap.get("name"));
        System.out.println(JSON.toJSONString(multimap));
        System.out.println(JSON.toJSONString(multimap.asMap()));
    }

    private static void testMultiset() {
        String str = "张三 李四 李四 王五 王五 王五";
        String[] strArr = str.split(" ");
        List<String> words = new ArrayList<String>(Arrays.asList(strArr));
        Multiset<String> multiset = HashMultiset.create();
        multiset.addAll(words);
        multiset.add("张三");
        multiset.add("啊三",5);
        multiset.remove("啊三",6);
        System.out.println(JSON.toJSONString(multiset));
        System.out.println(multiset.count("啊三"));
    }
}
