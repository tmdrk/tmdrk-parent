package org.tmdrk.interview;

import java.util.Arrays;

/**
 * SearchRange
 * 在排序数组中查找元素的第一个和最后一个位置
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 *
 * 你的算法时间复杂度必须是 O(log n) 级别。
 *
 * 如果数组中不存在目标值，返回 [-1, -1]。
 *
 * 示例 1:
 *
 * 输入: nums = [5,7,7,8,8,10], target = 8
 * 输出: [3,4]
 * 示例 2:
 *
 * 输入: nums = [5,7,7,8,8,10], target = 6
 * 输出: [-1,-1]
 *
 * @author Jie.Zhou
 * @date 2020/11/6 13:30
 */
public class SearchRange {
    public static void main(String[] args) {
        int[] nums = new int[]{5,7,7,8,8,10};
        int target = 8;
        int[] ints = searchRange(nums, target);
        Arrays.stream(ints).forEach(System.out::print);
    }
    public static int[] searchRange(int[] nums, int target) {
        int[] res = new int[]{-1,-1};
        int start=0;
        int end = nums.length;
        while (true){
            int mid = (start+end)/2;
            if(nums[mid]==8 && nums[mid-1]<8){
                res[0] = mid-1;
            }else if(nums[mid]>8){
            }
            break;
        }
        return res;
    }
}
