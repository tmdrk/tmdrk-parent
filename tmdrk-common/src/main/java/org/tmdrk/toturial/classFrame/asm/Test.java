package org.tmdrk.toturial.classFrame.asm;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Test
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/6 17:06
 * @Version 1.0
 **/
public class Test {
    byte testByte = 1;
    short testShort = 2;
    int testInt = 3;
    long testLong = 4;
    float testFloat = 5;
    double testDouble = 6;
    boolean testBoolean = false;
    char testChar = 'b';
    String NAME = "zhoujie";
    Map<String,Object> map = new HashMap<>();

    int[] testInts = {11,12};

    void helloWorld(){
        testByte = 2;
    }
    int getTestByte(){
        testByte = 2;
        return testByte;
    }

    String stringAddInt(int a,String b){
        b = b + a;
        return b;
    }

}
