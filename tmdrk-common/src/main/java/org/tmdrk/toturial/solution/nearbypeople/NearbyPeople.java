package org.tmdrk.toturial.solution.nearbypeople;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName NearbyPeople
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/17 18:34
 * @Version 1.0
 **/
public class NearbyPeople {
    static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
//        Thread.sleep(10000L);
        System.out.println("start...");
        String userName = "耶稣";
        List<UserInfo> list = new ArrayList<>(10000000);
        List<UserInfo> distanceList1 = new ArrayList<>(1000);//0-100
        List<UserInfo> distanceList2 = new ArrayList<>(1000);//100-200
        List<UserInfo> distanceList3 = new ArrayList<>(1000);//200-500
        List<UserInfo> distanceList4 = new ArrayList<>(1000);//500-2000
        List<UserInfo> distanceList5 = new ArrayList<>(1000);//2000以上
        UserInfo myUserInfo = new UserInfo(userName,randomFloat(180,2),randomFloat(90,2));
        long start1 = System.currentTimeMillis();
        for(int i=0;i<10000000;i++){
            UserInfo userInfo = new UserInfo(userName+i,randomFloat(180,2),randomFloat(90,2));
            list.add(userInfo);
        }
        System.out.println(list.size()+":"+(System.currentTimeMillis()-start1));
        long start = System.currentTimeMillis();
        for(UserInfo userInfo :list){
            double tmp = Math.pow(userInfo.getLongitude()-myUserInfo.getLongitude(),2)+Math.pow(userInfo.getLatitude()-myUserInfo.getLatitude(),2);
            double distance = Math.sqrt(tmp);
            UserInfo userInf = null;
            if(distance<1){
                userInf = new UserInfo(userInfo.getUserName(),userInfo.getLongitude(),userInfo.getLatitude(),distance);
                distanceList1.add(userInf);
            }else if(distance<2){
                if(distanceList1.size()>1000||(distanceList1.size()+distanceList2.size())>1000){
                    continue;
                }
                userInf = new UserInfo(userInfo.getUserName(),userInfo.getLongitude(),userInfo.getLatitude(),distance);
                distanceList2.add(userInf);
            }else if(distance<4){
                if((distanceList1.size()+distanceList2.size())>1000||(distanceList1.size()+distanceList2.size()+distanceList3.size())>1000){
                    continue;
                }
                userInf = new UserInfo(userInfo.getUserName(),userInfo.getLongitude(),userInfo.getLatitude(),distance);
                distanceList3.add(userInf);
            }else if(distance<8){
                if((distanceList1.size()+distanceList2.size()+distanceList3.size())>1000||(distanceList1.size()+distanceList2.size()+distanceList3.size()+distanceList4.size())>1000){
                    continue;
                }
                userInf = new UserInfo(userInfo.getUserName(),userInfo.getLongitude(),userInfo.getLatitude(),distance);
                distanceList4.add(userInf);
            }else{
                if(((distanceList1.size()+distanceList2.size()+distanceList3.size()+distanceList4.size())>1000)||(distanceList1.size()+distanceList2.size()+distanceList3.size()+distanceList4.size()+distanceList5.size())>1000){
                    continue;
                }
                userInf = new UserInfo(userInfo.getUserName(),userInfo.getLongitude(),userInfo.getLatitude(),distance);
                distanceList5.add(userInf);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start));
        System.out.println(distanceList1.size());
        System.out.println(distanceList2.size());
        System.out.println(distanceList3.size());
        System.out.println(distanceList4.size());
        System.out.println(distanceList5.size());
    }

    public static float randomFloat(int range,int scale){
        int tem = random.nextInt(new Double(range*Math.pow(10,scale)).intValue());
        int symbol = random.nextInt(2)==0?-1:1;
        return tem/100*symbol;
    }
}
