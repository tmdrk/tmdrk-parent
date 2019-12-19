package org.tmdrk.common;

/**
 * @ClassName CpuCoreUtil
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/6/29 21:46
 * @Version 1.0
 **/
public class CpuCoreUtil {
    public static void main(String[] args) {
        int availProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("avail processors count: " + availProcessors);
    }

    public static int getAvailableProcessors(){
        return Runtime.getRuntime().availableProcessors();
    }
}
