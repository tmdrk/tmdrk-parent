package org.tmdrk.toturial.base;

/**
 * @ClassName IntegerTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/3/24 17:18
 * @Version 1.0
 **/
public class IntegerTest {
    public static void main(String[] args) {
        String s = Integer.toBinaryString(-2145714174);
        System.out.println(s);
        s = "10000000000110110000000000000011";

        String s2 = Integer.toBinaryString(-2145714175);
        System.out.println(s2);
        String s3 = Integer.toBinaryString(-2145714176);
        System.out.println(s3);
        String s4 = Integer.toBinaryString(-2145714177);
        System.out.println(s4);

        String s1 = Integer.toBinaryString(-5);
        System.out.println(s1);
        s1 = "11111111111111111111111111111011";
        s1 = "10000000000000000000000000000101";//源码
        s1 = "11111111111111111111111111111010";//反码
        s1 = "11111111111111111111111111111011";//补码

        String s5 = Integer.toBinaryString(5);
        System.out.println(s5);
        s5 = "00000000000000000000000000000101";

        System.out.println(Integer.numberOfLeadingZeros(16));
        int t = 27;
        String s6 = Integer.toBinaryString(32795);
        System.out.println(s6);
        s6 = "00000000000000001000000000011011";
        System.out.println();
    }
}
