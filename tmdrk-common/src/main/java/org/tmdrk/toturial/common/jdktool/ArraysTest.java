package org.tmdrk.toturial.common.jdktool;

import java.util.Arrays;

/**
 * @ClassName ArraysTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/6/11 3:04
 * @Version 1.0
 **/
public class ArraysTest {
    public static void main(String[] args) {
        int[] arr = new int[]{3,5,6,3,1};
        //    public static void parallelSort(int[] a) {
        //        int n = a.length, p, g;
        //        if (n <= MIN_ARRAY_SORT_GRAN ||
        //            (p = ForkJoinPool.getCommonPoolParallelism()) == 1)
        //            DualPivotQuicksort.sort(a, 0, n - 1, null, 0, 0);
        //        else
        //            new ArraysParallelSortHelpers.FJInt.Sorter
        //                (null, a, new int[n], 0, n, 0,
        //                 ((g = n / (p << 2)) <= MIN_ARRAY_SORT_GRAN) ?
        //                 MIN_ARRAY_SORT_GRAN : g).invoke();
        //    }
        //排序超过阈值才使用并发排序
        Arrays.parallelSort(arr);
    }
}
