package org.tmdrk.toturial.collection.bitset;

import java.util.BitSet;

/**
 * @ClassName BitsetTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/9/11 0:21
 * @Version 1.0
 **/
public class BitsetTest {
    public static void main(String[] args) {
        BitSet bm=new BitSet();
        BitSet bm1=new BitSet();
        bm1.set(0);
        System.out.println(bm.isEmpty()+"--"+bm.size());
        bm.set(0);
        System.out.println(bm.isEmpty()+"--"+bm.size());
        bm.set(1);
        System.out.println(bm.isEmpty()+"--"+bm.size());
        System.out.println(bm.get(1));
        System.out.println(bm.isEmpty()+"--"+bm.size());
        bm.set(65);
        System.out.println(bm.isEmpty()+"--"+bm.size());
        bm.clear(1);
        bm.and(bm1);
        printBitSet(bm);
    }

    //打印
    public static void printBitSet(BitSet bs){
        StringBuffer buf=new StringBuffer();
        buf.append("[\n");
        for(int i=0;i<bs.size();i++){
            if(i<bs.size()-1){
                buf.append(BitsetTest.getBitTo10(bs.get(i))+",");
            }else{
                buf.append(BitsetTest.getBitTo10(bs.get(i)));
            }
            if((i+1)%8==0&&i!=0){
                buf.append("\n");
            }
        }
        buf.append("]");
        System.out.println(buf.toString());
    }

    //true,false换成1,0为了好看
    public static String getBitTo10(boolean flag){
        String a="";
        if(flag==true){
            return "1";
        }else{
            return "0";
        }
    }

}
