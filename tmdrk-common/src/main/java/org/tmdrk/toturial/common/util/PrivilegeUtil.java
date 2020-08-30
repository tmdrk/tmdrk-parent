package org.tmdrk.toturial.common.util;

import com.google.inject.internal.util.$SourceProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 权限工具类 (支持十进制和62进制)
 * 前提，权限最好是从1开始增长的自然数
 * 可将几百个权限转换成一个flag串101...01，每一位代表一个权限位
 * 用十进制位运算判断权限是否存在
 */
public class PrivilegeUtil {
    //正则，这里用来判断是十进制还是62进制
    private static Pattern regNumber = Pattern.compile("\\d+");

    //62进制配置
    private static char[]     chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static BigInteger scale = BigInteger.valueOf(62);

    private PrivilegeUtil(){}

    public static void main(String[] args) {
//        int[] arr = {1,3,12,34,67,345};
        int[] arr = {1,3,5,12};
        BigInteger flag = getBigInteger(arr);

        System.out.println("flag:"+flag);

        System.out.println(checkPrivilege(11));

        String fl = "66749594872528440306428606792430945853632453496403372492101169265089553048077036810770837871106";
//        String fl = "71671831749689734737838152978190216899892655911508785116799651230841339877765150252335653737298283991050";
        getPriva(fl);

    }

    private static int[] getPriva(String fl) {
        BigInteger big = new BigInteger(fl);
        System.out.println(big.bitLength());
        int[] pri = new int[big.bitCount()];
        int index = 0;
        for(int i=1;i<=big.bitLength();i++){
            if(big.shiftRight(i).and(BigInteger.ONE).compareTo(BigInteger.ONE) == 0){
                pri[index++] = i;
                System.out.print(i+" ");
            }
        }
        return pri;
    }

    /**
     * 将用户权限转化成flag标志
     *  权限数组 arr = {1,3,5,12};
     *  二进制flag:1000000101010
     *  十进制flag:4138
     * @param arr:
     * @return: java.math.BigInteger
     **/
    private static BigInteger getBigInteger(int... arr) {
        BigInteger      flag       = BigInteger.ZERO;
        for(int a:arr){
            flag = BigInteger.ONE.shiftLeft(a).or(flag);
        }
        System.out.println("二进制flag:"+flag.toString(2));
        return flag;
    }

    /**
     * 检查权限是否存在
     * 检查目标权限所在bit位是否是1，是则有权限
     * 如 1011011110110111000011110011 入参若是8，则左移8位查看第一位是否为1
     * @param flags:
     * @return: boolean
     **/
    public static boolean checkPrivilege(int ...flags) {
        String flag = "71671831749689734737838152978190216899892655911508785116799651230841339877765150252335653737298283991050";
        BigInteger f = null;
        if(regNumber.matcher(flag).matches()){
            f = new BigInteger(flag);
        }else{
            f = decode(flag);
        }
        System.out.println(f.toString(2));
        // 第一位是超级管理
        if (f.and(BigInteger.ONE).compareTo(BigInteger.ONE) == 0) {
            return true;
        }
        for (int po : flags) {
            if (f.shiftRight(po).and(BigInteger.ONE).compareTo(BigInteger.ONE) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 62进制解码成10进制
     * 对于一个任意进制的串 10110
     * 二进制转十进制      1*2^4+0*2^3+1*2^2+1*2^1+0*2^0
     * 三进制转十进制      1*3^4+0*3^3+1*3^2+1*3^1+0*3^0
     * 十进制转十进制      1*10^4+0*10^3+1*10^2+1*10^1+0*10^0
     * 六十二进制转十进制   1*62^4+0*62^3+1*62^2+1*62^1+0*62^0
     *
     * @param str:
     * @return: java.math.BigInteger
     **/
    private static BigInteger decode(String str) {
        BigInteger sum = BigInteger.ZERO;
        int        len = str.length();
        for (int i = 0; i < len; i++) {
            int index = indexDigits(str.charAt(len - i - 1));
            BigInteger s = BigInteger.valueOf(index).multiply(scale.pow(i));
            sum = sum.add(s);
        }
        return sum;
    }

    /**
     * 获取char对应的十进制索引 例如 B->37
     * @param ch:
     * @return: int
     **/
    private static int indexDigits(char ch) {
        for (int i = 0; i < chars.length; i++) {
            if (ch == chars[i]) {
                return i;
            }
        }
        return -1;
    }
}
