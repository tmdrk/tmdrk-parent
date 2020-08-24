package org.tmdrk.toturial.common.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * TODO
 *
 * @author Jie.Zhou
 * @date 2020/8/19 18:12
 */
public class HutoolDateUtil {
    public static void main(String[] args) {
        Date date = org.tmdrk.toturial.common.util.DateUtil.parseDate("2020-07-24 09:26:50");
        long betweenMonth = DateUtil.betweenMonth(date, new Date(), true);
        System.out.println("betweenMonth:"+betweenMonth);
        long cycle = betweenMonth / 1;
        if (betweenMonth % 1 == 0) {
            System.out.println("cycle:"+cycle);
        }else{
            System.out.println("cycle+1:"+(cycle+1));
        }
        Date date1 = org.tmdrk.toturial.common.util.DateUtil.parseDate("2020-08-10 09:47:00");
        long between = DateUtil.between(date1, new Date(), DateUnit.MS);
        System.out.println("between:"+between);
        long cycleMillis = 10    *60 * 60 * 1000*24;
        long cycle2 = between / cycleMillis;
        System.out.println("between / cycleMillis:"+cycle2);
        if (between % cycleMillis == 0) {
            System.out.println("cycle2:"+cycle2);
        }else{
            System.out.println("cycle2+1:"+(cycle2+1));
        }
    }
}
