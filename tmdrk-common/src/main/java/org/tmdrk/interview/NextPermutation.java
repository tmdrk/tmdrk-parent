package org.tmdrk.interview;

import java.util.Arrays;

/**
 * NextPermutation
 * 下一个排列
 * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
 *
 * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
 *
 * 必须原地修改，只允许使用额外常数空间。
 *
 * 以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
 * 1,2,3 → 1,3,2
 * 3,2,1 → 1,2,3
 * 1,1,5 → 1,5,1
 *
 * @author Jie.Zhou
 * @date 2020/11/4 10:15
 */
public class NextPermutation {
    public static void main(String[] args) {
        int[] nums = new int[]{2,2,7,5,4,3,2,2,1};
//        int[] nums = new int[]{1,3,4,2};
//        int[] nums = new int[]{4,1,3,2,0,0};
        nextPermutation(nums);
        Arrays.stream(nums).forEach(n->{
            System.out.print(n);
        });
    }

    public static void nextPermutation(int[] nums) {
        if(nums == null || nums.length==0){
            return;
        }
//        for(int i=0;i<nums.length;i++){
//            System.out.print(nums[i]);
//            if(i!=nums.length-1){
//                System.out.print(",");
//            }else{
//                System.out.print(" → ");
//            }
//        }
        int swap = nums.length;
        for(int i=nums.length-1;i>0;i--){
            if(nums[i]>nums[i-1]){
//                int tmp = nums[i];
//                nums[i] = nums[i-1];
//                nums[i-1] = tmp;
                swap = i;
                for(int j=i+1;j<nums.length;j++){
                    if(nums[j]>nums[i-1]){
                        swap = j;
                    }else{
                        break;
                    }
                }
            }
            if(i==1 && swap == nums.length){
                //已经是最大值,倒序
                flip(nums,0,nums.length);
                break;
            }else if(swap != nums.length){
                int tmp = nums[swap];
                nums[swap] = nums[i-1];
                nums[i-1] = tmp;
                flip(nums,i,nums.length);
                break;
            }

        }

//        for(int i=0;i<nums.length;i++){
//            System.out.print(nums[i]);
//            if(i!=nums.length-1){
//                System.out.print(",");
//            }
//        }
    }

    public static void flip(int[] nums,int start,int end){
        int idx = 0;
        while(idx<(end - start)/2){
            int tmp = nums[idx];
            nums[idx] = nums[end-idx-1];
            nums[end-idx-1] = tmp;
            idx++;
        }
    }
}
