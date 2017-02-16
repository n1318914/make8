package com.yundaren.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {
	public static final String DATA_FORMAT_STR = "yyyy-MM-dd";
	public static final String DATA_TIME_FORMAT_STR = "yyyy-MM-dd HH:mm";
	public static final String DATA_STAND_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 时间字符串转DATE对象
	 */
	public static Date parseDateFromString(String input, String simpleformat) {
		if (StringUtils.isBlank(simpleformat)) {
			simpleformat = DATA_FORMAT_STR;
		}
		if (StringUtils.isBlank(input)) {
			return null;
		}
		try {
			DateFormat format = new SimpleDateFormat(simpleformat);
			Date date = format.parse(input);
			return date;
		} catch (Exception e) {
			log.error("input time format error , input: " + input + ", format:" + simpleformat, e);
			throw new RuntimeException();
		}
	}

	/**
	 * 长整形时间转时间字符串
	 */
	public static String formatTime(long input, String simpleformat) {
		if (StringUtils.isBlank(simpleformat)) {
			simpleformat = DATA_FORMAT_STR;
		}
		SimpleDateFormat formate = new SimpleDateFormat(simpleformat);
		return formate.format(input);
	}

	/**
	 * 时间转字符串
	 */
	public static String formatTime(Date input, String simpleformat) {
		if (StringUtils.isBlank(simpleformat)) {
			simpleformat = DATA_FORMAT_STR;
		}
		SimpleDateFormat formate = new SimpleDateFormat(simpleformat);
		return formate.format(input);
	}
	
	public static String getNow() {
		return formatTime(new Date(), DATA_STAND_FORMAT_STR);
	}

	/**
	 * 获取当前7天后的具体日期
	 */
	public static Date getAfter7DaysDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		String newDateStr = formatTime(calendar.getTime(), DATA_FORMAT_STR) + " 23:59:59";

		return parseDateFromString(newDateStr, DATA_STAND_FORMAT_STR);
	}
	/**
	 * 差多少月份
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getMonth(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }
	
	 /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
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
    
    public static  boolean isValidDate(String str){
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	if(str==null)return false;
        try{
            Date date = (Date)formatter.parse(str);
            return str.equals(formatter.format(date));
        }catch(Exception e){
           return false;
        }
    }

}
