package org.tmdrk.interview;


import com.google.common.collect.Maps;

import java.util.HashMap;

/**
 * @ClassName Test
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/5/14 9:45
 * @Version 1.0
 **/
public class Test {
    static HashMap<String, Object> staticMap = Maps.newHashMap();
    HashMap<String, Object> map = Maps.newHashMap();
    int index = 12;
    public static void main(String[] args) {
        Test test1 = new Test();
        Test test2 = new Test();

        test1.map.put("name","张三");
        test2.map.put("age","12");
        test1.index = 14;
        test2.index = 26;
        System.out.println(test1.map.get("age")+" "+test1.index);
    }
}

