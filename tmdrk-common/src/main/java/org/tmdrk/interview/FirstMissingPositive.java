package org.tmdrk.interview;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName FirstMissingPositive
 * 给你一个未排序的整数数组，请你找出其中没有出现的最小的正整数。
 * 示例 1:
 *
 * 输入: [1,2,0]
 * 输出: 3
 * 示例 2:
 *
 * 输入: [3,4,-1,1]
 * 输出: 2
 * 示例 3:
 *
 * 输入: [7,8,9,11,12]
 * 输出: 1
 *
 * 提示：
 *
 * 你的算法的时间复杂度应为O(n)，并且只能使用常数级别的额外空间。
 *
 * @Date 2020/11/8 23:17
 * @Version 1.0
 **/
public class FirstMissingPositive {
    public static void main(String[] args) {
        int[] nums = new int[]{3,4,-1,1};
        int i = firstMissingPositive(nums);
        System.out.println(i);
    }
    public static int firstMissingPositive(int[] nums) {
        Set<Integer> set = new HashSet(nums.length);
        for(int i=0;i<nums.length;i++){
            if(nums[i]>0){
                set.add(nums[i]);
            }
        }
        int i = 1;
        while(true){
            if(!set.contains(i)){
                return i;
            }
            i++;
        }
    }
}
