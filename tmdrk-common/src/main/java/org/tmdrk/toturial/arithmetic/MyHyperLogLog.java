package org.tmdrk.toturial.arithmetic;

import org.tmdrk.toturial.arithmetic.encryption.CRC.CRC16M;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName MyHyperLogLog
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/8 22:28
 * @Version 1.0
 **/
public class MyHyperLogLog {
    public static final int M = (2<<13) -1;

    static Map<Integer,Integer> map = new HashMap();

    public static void main(String[] args) {
        int key;
        for (Integer i=0;i<10000;i++){
            key = i & M;
            int maxOnePosition = getMaxOnePosition(oneByOneHash(i+"tkaksjhh"));
            if(map.containsKey(key)){
                map.put(key,Math.max(map.get(key),maxOnePosition));
            }else{
                map.put(key,maxOnePosition);
            }
        }

//        map.forEach((k,v)->{});
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        double sum = 0;
        double thsum = 0;
        int max = 0;
        int keys = map.size();
        while(iterator.hasNext()){
            int val = iterator.next().getValue();
            sum += val;
            if(val>0){
                thsum += 1D/new Double(val);
            }else{
                keys--;
            }
//            System.color.println(thsum);
            max = Math.max(max,val);
        }
        System.out.println(thsum);
        thsum = keys/thsum;
        double avg = sum/keys;
        System.out.println("keys:"+keys+" sum:"+sum+" thsum:"+thsum+" avg:"+avg+" max:"+max+" total:"+keys*Math.pow(2,avg)+" totals:"+Math.pow(2,max)+" totalth:"+keys*Math.pow(2,thsum));
        System.out.println(Math.pow(2,16));
    }

    public static int getMaxOnePosition(int a){
        char[] s = Integer.toBinaryString(a).toCharArray();
        int pos = 1;
        for (int i=s.length-1; i > 0;i--){
            if(s[i]==49){
//                System.color.println("pos:"+pos);
                break;
            }else{
                pos++;
            }
        }
        return pos-1;
    }

    public static int oneByOneHash(String key)
    {
        int  hash,i;
        for (hash=0,i=0; i<key.length(); ++i)
        {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        //  return (hash & M_MASK);
        if(hash<0){
            hash*=-1;
        }
        return hash;
    }
}
