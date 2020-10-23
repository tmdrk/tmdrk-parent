package org.tmdrk.business.cycle;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * CycleUtil
 *
 * @author Jie.Zhou
 * @date 2020/10/15 10:04
 */
public class CycleUtil {
    public static void main(String[] args) {
        long indexHour = getCycleIndex("hour", 3, DateUtil.offset(new Date(), DateField.HOUR,-3));
        System.out.println("indexHour:"+indexHour);

        long indexDay = getCycleIndex("day", 1, DateUtil.offset(new Date(), DateField.HOUR,-25));
        System.out.println("indexDay:"+indexDay);

        long indexWeek = getCycleIndex("week", 1, DateUtil.offset(new Date(), DateField.HOUR,-24*7-1));
        System.out.println("indexWeek:"+indexWeek);

        long indexMonth = getCycleIndex("month", 1, DateUtil.offset(new Date(), DateField.HOUR,-24*14));
        System.out.println("indexMonth:"+indexMonth);

        System.out.println(DateUtil.offset(new Date(), DateField.HOUR,-24*14));
    }

    /**
     * 获取当前周期
     * @param unit: 周期单位
     * @param cycleNum: 周期数
     * @param startTime: 开始时间
     * @return: long
     */
    public static long getCycleIndex(String unit,int cycleNum,Date startTime) {
        CycleUnit cycleUnit = CycleUnit.parse(unit);
        if (cycleUnit == null || cycleUnit == CycleUnit.PERPETUAL) {
            return 1;
        }
        Date now = new Date();
        if (cycleUnit == CycleUnit.MONTH) {
            // 月份天数不一致 周期单独计算
            long betweenMonth = DateUtil.betweenMonth(startTime, now, true);
            long cycleMonth = cycleNum;
            long cycle = betweenMonth / cycleMonth;
            if (betweenMonth % cycleMonth == 0) {
                return cycle;
            }
            return cycle + 1;
        }
        long between = DateUtil.between(startTime, now, DateUnit.MS);
        long cycleMillis = cycleNum * cycleUnit.getMillis();
        long cycle = between / cycleMillis;
        if (between % cycleMillis == 0) {
            return cycle;
        }
        return cycle + 1;
    }
}
