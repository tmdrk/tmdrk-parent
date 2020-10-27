package org.tmdrk.interview;

import java.util.HashMap;
import java.util.Map;

/**
 * RotatingNumbers
 *  1  2  3  4
 *  5  6  7  8
 *  9  10 11 12
 *  13 14 15 16
 *  顺时针旋转打印 1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10
 *
 *  找到中心点，向外层递归，后置打印表示由外向内旋转，前置打印表示由内向外旋转
 * @author Jie.Zhou
 * @date 2020/10/27 9:37
 */
public class RotatingNumbers {
    static Map<String,String> map = new HashMap<>();
    static {
        map.put("right","top");
        map.put("top","left");
        map.put("left","bottom");
        map.put("bottom","right");
    }
    static int cnt = 0;
    public static void main(String[] args) {
        int len = 2;
        int[][] aa = new int[len][len];
        for(int i=0;i<len;i++){
            for(int j=0;j<len;j++){
                aa[i][j]=len*i+j+1;
            }
        }
        startRotate(aa);
    }

    private static void startRotate(int[][] aa) {
        String position = "left";
        if(aa.length%2==0){
            position = "right";
        }
        int count = 0; //step计数器，每走两个弯重置，step+1
        int stepTotal = 1;
        int step = 0; //每个方向走几步
        int i = aa.length/2;
        int j = aa.length%2==0?aa.length/2-1:aa.length/2;
        rotate(aa,position,stepTotal,step,count,i,j);
        System.out.println("aa["+i+"]["+j+"] = "+aa[i][j]);
    }

    private static void rotate(int[][] aa, String position, int stepTotal, int step, int count,int i,int j) {
        cnt++;
        step++;
        if(cnt >= aa.length*aa.length){
            System.out.println("结束");
            return;
        }
        switch (position){
            case "right":
                j++;break;
            case "top":
                i--;break;
            case "left":
                j--;break;
            case "bottom":
                i++;break;
        }
        if(step<stepTotal){
            //不转向
            rotate(aa,position,stepTotal,step,count,i,j);
        }else{
            //转向
            step = 0; //转向重置step
            count++;
            if(count==2){
                stepTotal++; //总步长变化需重置count
                count=0;
            }
            rotate(aa,map.get(position),stepTotal,step,count,i,j);
        }
        System.out.println("aa["+i+"]["+j+"] = "+aa[i][j]);
    }
}
