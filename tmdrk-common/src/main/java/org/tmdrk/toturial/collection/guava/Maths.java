package org.tmdrk.toturial.collection.guava;

import com.google.common.math.BigIntegerMath;
import com.google.common.math.IntMath;

import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Maths
 *
 * @author Jie.Zhou
 * @date 2020/8/11 15:31
 */
public class Maths {
    public static void main(String[] args) {
//        IntMath.checkedAdd(Integer.MAX_VALUE, Integer.MAX_VALUE);
        System.out.println(IntMath.checkedSubtract(5,3));
        BigIntegerMath.sqrt(BigInteger.TEN.pow(99), RoundingMode.HALF_EVEN);

        System.out.println(IntMath.gcd(24,32));
        System.out.println(IntMath.mod(24,3));
        System.out.println(IntMath.pow(2,3));
        System.out.println(IntMath.isPowerOfTwo(4546));
        System.out.println(IntMath.factorial(5));
        System.out.println(IntMath.binomial(4,2));
    }
}
