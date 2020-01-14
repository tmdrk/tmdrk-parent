package com.tmdrk.chat.common.utils;
  
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//import org.apache.commons.lang.StringUtils;
  
/**
 * @description DateUtils.java 日期处理相关工具类
 * @author shiyang
 * 2016年10月22日 下午2:41:21
 */
public class DateUtils {
      
    /**定义常量**/
    public static final String DATE_JFP_STR="yyyyMM";
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_KEY_STR = "yyMMddHHmmss";
    public static final String DATE_Y_M_STR = "yyyy-MM";
      
    /**
     * 使用预设格式提取字符串日期
     * @param strDate 日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate,DATE_FULL_STR);
    }
    
    /**
     * 按指定格式将字符串转换为日期
     * 
     * @param dateStr
     *            日期串
     * @param pattern
     *            格式
     * @return 日期
     */
    public static Date strDate(String dateStr, String pattern){
        if (null == dateStr) {
            return null;
        }
        if (null == pattern) {
            pattern = DATE_SMALL_STR;
        }
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
      
    /**
     * 使用用户格式提取字符串日期
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
     
    /**
     * 两个日期天数差
     * @param
     * @return
     */
	public static int daysBetween(Date smdate,Date bdate) throws ParseException {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    } 
	
    /**
     * 两个时间比较
     * @param
     * @return
     */
    public static int compareDateWithNow(Date date1){
        Date date2 = new Date();
        int rnum =date1.compareTo(date2);
        return rnum;
    }
      
    /**
     * 两个时间比较(时间戳比较)
     * @param
     * @return
     */
    public static int compareDateWithNow(long date1){
        long date2 = dateToUnixTimestamp();
        if(date1>date2){
            return 1;
        }else if(date1<date2){
            return -1;
        }else{
            return 0;
        }
    }
      
  
    /**
     * 获取系统当前时间
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
        return df.format(new Date());
    }
      
    /**
     * 获取系统当前时间
     * @return
     */
    public static String getNowTime(String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
        return df.format(new Date());
    }
      
    /**
     * 获取系统当前计费期
     * @return
     */
    public static String getJFPTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_JFP_STR);
        return df.format(new Date());
    }
      
    /**
     * 将指定的日期转换成Unix时间戳
     * @param date 需要转换的日期 yyyy-MM-dd HH:mm:ss
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
    
    /**
     * java.util.Date 转 string
     * @param format
     * @param date
     * @return
     */
    public static String dateToString(String format,Date date){
    	String str = "--";
    	try{
    		str = new SimpleDateFormat(format).format(date);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return str;
    }
    
    
    /**
     * 将指定的日期转换成Unix时间戳
     * @param  date 需要转换的日期 yyyy-MM-dd
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date, String dateFormat) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
      
    /**
     * 将当前日期转换成Unix时间戳
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp() {
        long timestamp = new Date().getTime();
        return timestamp;
    }
      
      
    /**
     * 将Unix时间戳转换成日期
     * @param  timestamp 时间戳
     * @return String 日期字符串
     */
    public static String unixTimestampToDate(long timestamp) {
        SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
        sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sd.format(new Date(timestamp));
    }
	/**
	 * 获取创建时间格式
	 * @author zhangyinghua
	 * @Date 2016-10-25
	 * @version v1.0.0
	 * @return
	 */
	public static String getCreateTime() {
		return System.currentTimeMillis() + "";
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getDateTime(String formatter){
		String str = "";
		try{
			DateFormat format = new SimpleDateFormat(formatter);
			str = format.format(new Date());
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 判断两个日期是否为同一天
	 */
	public static boolean isSameDate(Date date1, Date date2) {
	       Calendar cal1 = Calendar.getInstance();
	       cal1.setTime(date1);
	       
	       Calendar cal2 = Calendar.getInstance();
	       cal2.setTime(date2);
	       boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
	               .get(Calendar.YEAR);
	       boolean isSameMonth = isSameYear
	               && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	       boolean isSameDate = isSameMonth
	               && cal1.get(Calendar.DAY_OF_MONTH) == cal2
	                       .get(Calendar.DAY_OF_MONTH);

	       return isSameDate;
	   }
	
	/**
	 * 月份根据条件变化
	 * @param date 年月2017-04
	 * @param step 增量
	 * @param strFormat 格式
	 * @return
	 * @author zhoujie
	 * @date 2017年2月25日 下午7:04:57
	 */
//	public static String getDateYM(String date,int step,String strFormat){
//		String str = "";
//		try{
//			if(StringUtils.isBlank(strFormat)){
//				strFormat = DATE_Y_M_STR;
//			}
//			DateFormat format = new SimpleDateFormat(strFormat);
//			Date dates = format.parse(date);
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(dates);
//			calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)+step);
//			str = format.format(calendar.getTime());
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return str;
//	}
//	public static String getDateYM(String date,int step){
//		return getDateYM(date,step,null);
//	}
	
	/**
	 * 天数根据条件变化
	 * @param date
	 * @param step
	 * @return
	 * @author zhoujie
	 * @date 2017年3月16日 下午9:56:59
	 */
	public static String getDateYMD(String date,int step){
		String str = "";
		try{
			DateFormat format = new SimpleDateFormat(DATE_SMALL_STR);
			Date dates = format.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dates);
			calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+step);
			str = format.format(calendar.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}

    private static Date getLastDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取上月 格式yyyyMM
     * @param date
     * @return
     */
    public static String getLastMonthYyyymm(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_JFP_STR);
        return sdf.format(getLastDate(date));
    }
	
	/** 
     * 得到几天前的时间 
     *  
     * @param d 
     * @param day 
     * @return 
     */  
    public static Date getDateBefore(Date d, int day) {  
        Calendar now = Calendar.getInstance();  
        now.setTime(d);  
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);  
        return now.getTime();  
    }


    public static Date getDateStartTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_SMALL_STR);
        SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_FULL_STR);
        String startTime = sdf.format(date) + " 00:00:00";
        try {
            return sdf1.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateEndTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_SMALL_STR);
        SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_FULL_STR);
        String startTime = sdf.format(date) + " 23:59:59";
        try {
            return sdf1.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


//    public static void main(String[] args) {
//        int a = 10;
//        int b = 43;
//        System.color.println(b/a);
//    }
}
 