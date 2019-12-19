package org.tmdrk.toturial;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName Test1
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/19 2:53
 * @Version 1.0
 **/
public class Test1 {
    private static final int[] SIZE_TABLE;

    static {
        List<Integer> sizeTable = new ArrayList<Integer>();
        for (int i = 16; i < 512; i += 16) {
            sizeTable.add(i);
        }

        for (int i = 512; i > 0; i <<= 1) {
            sizeTable.add(i);
        }

        SIZE_TABLE = new int[sizeTable.size()];
        for (int i = 0; i < SIZE_TABLE.length; i ++) {
            SIZE_TABLE[i] = sizeTable.get(i);
        }
    }

    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        int cap = 35;
        HashMap hashMap = new HashMap(cap);
        for(int i=0;i<SIZE_TABLE.length;i++){
            System.out.println(i+":"+SIZE_TABLE[i]);
        }
    }
}
