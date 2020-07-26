package org.tmdrk.toturial.collection;

import org.tmdrk.protobuf.DataInfo;

import java.util.*;
import java.util.function.IntBinaryOperator;

/**
 * @ClassName CollectionsAndArraysTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/4/2 22:07
 * @Version 1.0
 **/
public class CollectionsAndArraysTest {
    public static void main(String[] args) {
        /********** Working with Collections **********/

        //创建不可变的空集合
        Map<Object, Object> emptyMap = Collections.emptyMap();
//        emptyMap.put("name","zhoujie"); //Exception in thread "main" java.java.lang.UnsupportedOperationException
        System.out.println(emptyMap.getClass()+"|"+emptyMap.get("name"));

        //创建不可变的含一个元素的集合
        Map<String, String> singletonMap = Collections.singletonMap("name", "zhangsan");
//        singletonMap.put("name","wangwu");//Exception in thread "main" java.java.lang.UnsupportedOperationException
        System.out.println(emptyMap.getClass()+"|"+singletonMap.get("name"));

        Map<Object, Object> map = new HashMap<>();
        map.put("name","liliu");
        map.put("sex","man");
        Map<Object, Object> unmodifyableMap = Collections.unmodifiableMap(map);
        System.out.println(unmodifyableMap.getClass()+"|"+unmodifyableMap.get("name"));

        //排序
        Collections.sort(new ArrayList());
        Collections.sort(new ArrayList<DataInfo.Student>(), new Comparator<DataInfo.Student>() {
            @Override
            public int compare(DataInfo.Student o1, DataInfo.Student o2) {
                return 0;
            }
        });

        //还支持与排序无关的（重新）排序，例如颠倒顺序，旋转或移动所有元素以及随机调整内容：
        List list = Arrays.asList(3,56,7,88,5,34,2,7,8,85);
        Collections.reverse(list);
        Collections.rotate(list,2);
        Collections.shuffle(list);
        Collections.shuffle(list,new Random());

        //可以使用二分法搜索算法来查找特定元素的索引，而不是遍历集合：
        //使用二分搜索法搜索指定列表，以获得指定对象。在进行此调用之前，必须根据列表元素的自然顺序对列表进行升序排序（通过 sort(List) 方法）。
        //如果没有对列表进行排序，则结果是不确定的。如果列表包含多个等于指定对象的元素，则无法保证找到的是哪一个。
        List list2 = Arrays.asList(3,56,7,88,5,34,2,7,8,85);
        Collections.sort(list2);
        int i = Collections.binarySearch(list2, 88);
        System.out.println(i);

        //线程安全类
        //另一个限制是，如果集合被Iterator，Spliterator或Stream遍历，则需要手动同步。 遍历对象的创建必须在同步块内：
        Map<Object, Object> map1 = new HashMap<>();
        Map<Object, Object> synchronizedMap = Collections.synchronizedMap(map1);
        synchronized (synchronizedMap){
            //迭代
        }

        List list3 = new ArrayList();
        list3.add(34);list3.add(2);
        List<String> list4 = Collections.checkedList(list3, String.class);
        list4.add("67");
        System.out.println(list4);


        /********** Working with Arrays **********/
        List<String> fromArray = Arrays.asList("2","3");

        int[] mans = new int[]{34,46,757,22,56,76,89,44,45,89};
        Arrays.sort(mans,2,5);

        Arrays.stream(mans);
        Arrays.spliterator(mans);
        Arrays.deepToString(null);
        Arrays.deepEquals(null,null);
        Arrays.binarySearch(mans,45);
        Arrays.copyOf(mans,5);
        Arrays.fill(mans,2,5,66);
        Arrays.stream(mans).forEach(a-> System.out.println(a));
        Arrays.setAll(mans,a->a*3);
        System.out.println("--------------");
        Arrays.stream(mans).forEach(a-> System.out.println(a));
        System.out.println("--------------");
        Arrays.parallelPrefix(mans, new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left+right;
            }
        });
        Arrays.stream(mans).forEach(a-> System.out.println(a));
    }
}
