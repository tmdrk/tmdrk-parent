package org.tmdrk.toturial.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName DateTimeUtil
 * @Description 日期工具类
 * java 8 吸收了 Joda-Time 的精华
 * @Author zhoujie
 * @Date 2020/2/7 11:40
 * @Version 1.0
 **/
public class DateTimeUtil {
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * 东八区
     */
    private static final String ZONE_8 = "+8";



    public static void main(String[] args) throws InterruptedException {

        //now() 实例化
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        //of() 实例化
        LocalDate ld = LocalDate.of(2020, 2, 10);
        LocalDateTime ldt = LocalDateTime.of(2018, 2, 10,12,23,45);
        LocalTime lt = LocalTime.of(12, 23, 10);

        //getXXX() 获取相关属性
        System.out.println(ldt.getDayOfYear());
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getDayOfWeek());
        System.out.println(ldt.getMinute());
        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonth());

        //withXXX() 设置相关属性 具有不可变性，即原值不会被修改
        LocalDateTime ldt2 = ldt.withDayOfMonth(20);
        System.out.println(ldt);
        System.out.println(ldt2);

        //plusXXX 加上属性
        LocalDateTime ldt3 = ldt.plusDays(5);
        System.out.println(ldt3);

        //minusXXX 减去属性
        LocalDateTime ldt4 = ldt.minusDays(10);
        System.out.println(ldt4);

        /************ instant 类似于java.util.date类 ************/
        //now() 获取本初子午线对应的时间
        System.out.println("************ instant ************");
        Instant instant = Instant.now();// 差8小时，因为默认是格林威治时间，北京是东八区
        System.out.println(instant);
        System.out.println(instant.getEpochSecond());

        //atOffset() 添加时间偏移量
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);

        //toEpochMilli()  将Instant转换成时间戳 the number of milliseconds since the epoch of 1970-01-01T00:00:00Z
        System.out.println(instant.toEpochMilli());

        //ofEpochMilli() 将时间戳转换成Instant
        System.out.println(Instant.ofEpochMilli(1584384242910L));

        /************ instant end ************/

        /************ DateTimeFormatter 类似于SimpleDateTFormat类 ************/
        System.out.println("************ DateTimeFormatter 类似于SimpleDateTFormat类 ************");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;//标准格式
        //format()
        String format = formatter.format(localDateTime);
        System.out.println(format);

        //parse()
        TemporalAccessor parse = formatter.parse("2020-03-17T02:53:37.519");
        System.out.println(parse);

        //反向format
        String bid = localDateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
        String id = localDateTime.format(DateTimeFormatter.ISO_DATE);
        String idt = localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println(bid);
        System.out.println(id);
        System.out.println(idt);


        //ofLocalizedDateTime() 本地格式化解析
        //FormatStyle.LONG FormatStyle.MEDIUM FormatStyle.SHORT 三种参数格式
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        System.out.println(dateTimeFormatter1.format(localDateTime));
        DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        System.out.println(dateTimeFormatter2.format(localDateTime));
        DateTimeFormatter dateTimeFormatter3 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        System.out.println(dateTimeFormatter3.format(localDateTime));

        //ofLocalizedDate() 本地格式化解析
        //FormatStyle.FULL FormatStyle.LONG FormatStyle.MEDIUM FormatStyle.SHORT 四种参数格式
        DateTimeFormatter dateTimeFormatter4 = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        System.out.println(dateTimeFormatter4.format(localDateTime));

        //ofPattern() 自定义格式化解析
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format1 = dateTimeFormatter.format(localDateTime);
        System.out.println(format1);
        TemporalAccessor parse1 = dateTimeFormatter.parse(format1);
        System.out.println(parse1);


        /************ DateTimeFormatter end ************/

        /************ Zone 类 ************/
        System.out.println(ZoneId.systemDefault());
        //System.out.println(ZoneId.SHORT_IDS);

        /************ Zone 类 end ************/

        /************ Clock 类 ************/
        System.out.println("************ Clock 类 ************");
        // 系统默认
        Clock systemDefaultClock = Clock.systemDefaultZone();
        System.out.println("Current DateTime with system default clock: " + LocalDateTime.now(systemDefaultClock));
        // 世界协调时UTC
        Clock systemUTCClock = Clock.systemUTC();
        System.out.println("Current DateTime with UTC clock: " + LocalDateTime.now(systemUTCClock));
        //芝加哥
        Clock clock = Clock.system(ZoneId.of(ZoneId.SHORT_IDS.get("CST")));
        System.out.println("Current DateTime with CST clock: " + LocalDateTime.now(clock));

        System.out.println(systemDefaultClock.getZone());
        System.out.println(systemDefaultClock.millis());

        /************ Clock 类 end ************/



        System.out.println(DateTimeUtil.timeStamp());
        System.out.println(System.currentTimeMillis());
        System.out.println(DateTimeUtil.timeStamp(DateTimeUtil.localDateTime()));
        System.out.println(DateTimeUtil.timeStamp(DateTimeUtil.localDate()));
        System.out.println(DateTimeUtil.timeStamp(DateTimeUtil.localDate(),DateTimeUtil.localTime()));
        System.out.println(DateTimeUtil.timeStamp(new Date()));

        Date date1 = new Date();
        Thread.sleep(2345);
        Date date2 = new Date();
        Long diff = (date2.getTime() - date1.getTime())/1000;
        System.out.println("diff:"+diff.intValue());
    }

    public static LocalDateTime localDateTime(){
        return LocalDateTime.now();
    }
    public static LocalDate localDate(){
        return LocalDate.now();
    }
    public static LocalTime localTime(){
        return LocalTime.now();
    }

    public static LocalDateTime date2LocalDateTime(Date date){
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date date(){
        return new Date();
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime){
        return Date.from(localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
    public static Date localDate2Date(LocalDate localDate){
        return Date.from(localDate.atTime(0,0,0)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
    public static Date localDate2Date(LocalDate localDate,LocalTime localTime){
        return Date.from(localDate.atTime(localTime)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * @Author zhoujie
     * @Description 获取当前时间戳
     * @Date 16:22 2020/2/26
     * @Param []
     * @return long
     **/
    public static long timeStamp(){
        return Instant.now().getEpochSecond(); //Instant为格林威治时间,秒数却是对应时区的
    }

    public static long timeStamp(LocalDateTime localDateTime){
        return localDateTime.toInstant(ZoneOffset.of(ZONE_8)).toEpochMilli();
    }
    public static long timeStamp(LocalDate localDate){
        return localDate.atTime(0,0,0).toInstant(ZoneOffset.of(ZONE_8)).toEpochMilli();
    }
    public static long timeStamp(LocalDate localDate,LocalTime localTime){
        return localDate.atTime(localTime).toInstant(ZoneOffset.of(ZONE_8)).toEpochMilli();
    }
    public static long timeStamp(Date date){
        return date.getTime();
    }



}
