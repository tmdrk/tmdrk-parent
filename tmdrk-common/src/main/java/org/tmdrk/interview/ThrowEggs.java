package org.tmdrk.interview;

/**
 * ThrowEggs
 *  高楼扔鸡蛋
 * @author Jie.Zhou
 * @date 2020/10/30 9:23
 */
public class ThrowEggs {
    public static void main(String[] args) {
        int i = superEggDrop(2, 10);
        System.out.println(i);
    }
    public static int superEggDrop(int K, int N) {
        return throwEgg(K,N);
    }
    public static int throwEgg(int K,int N) {
        if(N==0 || N==1 || K==1){
            return N;
        }
        int count = N;
        for (int i = 1; i < N; i++) {
            //鸡蛋碎了 K-1,i-1 鸡蛋没碎 K,N-i
            count = Math.min(count,Math.max(throwEgg(K-1,i-1),throwEgg(K,N-i))+1);
        }
        return count;
    }
}
