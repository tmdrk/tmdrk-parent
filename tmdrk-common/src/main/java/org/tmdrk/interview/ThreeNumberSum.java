package org.tmdrk.interview;

import java.util.*;

/**
 * ThreeNumberSum
 *
 * @author Jie.Zhou
 * @date 2020/10/27 15:04
 */
public class ThreeNumberSum {
    public static void main(String[] args) {
        int[] nums = new int[]{};
        List<List<Integer>> lists = threeSum(nums);
        System.out.println("============");
        lists.stream().forEach(System.out::println);
    }

    private static List<List<Integer>> threeSum(int[] nums) {
        if(nums.length==0){
            return Collections.EMPTY_LIST;
        }
        Map<String,List<Integer>> res = new HashMap<>();
        LinkedList<Integer> list = new LinkedList<>();
        sum(nums,nums.length-1,list,true,res);
        list.removeLast();
        sum(nums,nums.length-1,list,false,res);
        List<List<Integer>> result = new ArrayList<>(res.values());
        return result;
    }

    private static void sum(int[] nums,int i,LinkedList<Integer> list,boolean flag,Map<String,List<Integer>> res) {
        if(i<0){
            return;
        }
        if(flag){
            list.add(nums[i]);
        }
        if(list.size()==3 && list.stream().mapToInt(v->v).sum()==0){
            System.out.println(list);
            List<Integer> integers = new ArrayList<>(list);
            Collections.sort(integers);
            res.put(integers.toString(),integers);
            return;
        }
        if(list.size()>=3){
            return;
        }
        //选
        if(i-1>=0){
            sum(nums,i-1,list,true,res);
            list.removeLast();
        }
        //不选
        if(i-1>0){
            sum(nums,i-1,list,false,res);
        }
    }
}
