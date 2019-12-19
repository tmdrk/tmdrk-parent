package org.tmdrk.toturial.javap;

/**
 * @ClassName SynchronizedTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/4/24 20:57
 * @Version 1.0
 **/
public class SynchronizedTest {
    private static final String username = "nihao周杰";
    static int count = 0;
    public static void main(String[] args) {

    }
    public static synchronized void countUp(){
        for(int i=0;i<5;i++){
            count++;
        }
        String nickname = "zhoujie";
        System.out.println(nickname);
    }
}
