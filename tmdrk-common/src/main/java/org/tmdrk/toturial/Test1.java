package org.tmdrk.toturial;

import java.util.*;

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

        List<String> list = Arrays.asList("aa", "bb", "cc", "dd", "ee","ff","gg","hh","ii");
        Random random = new Random();
        int ranInt = random.nextInt(list.size());
        for(int i=0;i<list.size();i++){
            String s = list.get(ranInt);
            System.out.println(s);
//            if(s.equals("cc")){
//                System.out.println("跳出");
//                break;
//            }
            ranInt = (ranInt+1)%list.size();
        }
    }
}
