package org.tmdrk.interview;

import java.util.HashMap;
import java.util.Map;

/**
 * ThrowEggs
 *  高楼扔鸡蛋
 * @author Jie.Zhou
 * @date 2020/10/30 9:23
 */
public class ThrowEggs {
    static Map<String,Integer> res = new HashMap<>();
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int i = superEggDrop(6, 1000);
        long duration = System.currentTimeMillis()-start;
        System.out.println(i);
        System.out.println("duration:"+duration);
    }
    public static int superEggDrop(int K, int N) {
        return throwEgg(K,N);
    }
    public static int throwEgg(int K,int N) {
        if(N==0 || N==1 || K==1){
            return N;
        }
        if(res.containsKey(K+"|"+N)){
            return res.get(K+"|"+N);
        }
        int count = N;
        for (int i = 1; i <= N; i++) {
            //鸡蛋碎了 K-1,i-1 鸡蛋没碎 K,N-i
            count = Math.min(count,Math.max(throwEgg(K-1,i-1),throwEgg(K,N-i))+1);
            res.put(K+"|"+N,count);
        }
        return count;
    }
}
