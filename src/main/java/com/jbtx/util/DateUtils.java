package com.jbtx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author xiaoyou
 * @title: DateUtils
 * @projectName financial
 * @description: 日期处理工具类
 * @date 2019/6/1214:51
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 字符串转日期
     *
     * @param str     字符串日期
     * @param pattern 日期格式（默认yyyy-MM-dd）
     * @return
     */
    public static Date strToDate(String str, String pattern) {
        if (StringUtil.isEmpty(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            logger.error("日期转换异常, message:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 日期格式化
     *
     * @param str
     * @param pattern
     * @return
     */
    public static String strToStr(String str, String pattern) {
        if (StringUtil.isEmpty(pattern)) {
            pattern = "yyyy年MM月dd日 ";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(DateUtils.strToDate(str, "yyyy-MM-dd"));

    }
    public static Date dateTodate(Date date, String pattern) {
        if (StringUtil.isEmpty(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            logger.error("日期转换异常, message:{}", e.getMessage());
        }
        return null;

    }

    public static String DateToStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(date);

    }

    /**
     * 获取当前日期（yyyyMMddHHmmss)
     *
     * @return
     */
    public static String getNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     * 获取当前日期
     *
     * @param format
     * @return
     */
    public static String getNow(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * 两个时间相除
     *
     * @return
     */
    public static String diffDate(Date startDate, Date endDate) {
        long between = endDate.getTime() - startDate.getTime();
//        long day = between / (24 * 60 * 60 * 1000);
//        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)));
        long s = (between / 1000 - min * 60);
        return min + ":" + s;
    }

    /**
     * 日期增加年
     *
     * @param date 日期
     * @param year 几年
     * @return
     */
    public static Date dateAddYear(Date date, int year) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year); //把日期往后增加一年，整数往后推，负数往前移
        date = calendar.getTime();
        System.out.println(date);
        return date;
    }

    /**
     * 日期增加年
     *
     * @param date 日期
     * @param second 秒
     * @return
     */
    public static Date dateAddSecond(Date date, int second) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second); //把日期往后增加一年，整数往后推，负数往前移
        date = calendar.getTime();
        System.out.println(date);
        return date;
    }

    /**
     * 几分钟后的时间
     *
     * @param minute 几分钟
     * @return
     */
    public static Date dateAddminute(int minute) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute); //把日期往后增加一年，整数往后推，负数往前移
        date = calendar.getTime();
        System.out.println(date);
        return date;
    }


    public static void main(String[] args) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1); //把日期往后增加一年，整数往后推，负数往前移
        date = calendar.getTime();
        System.out.println(date);
    }
}
