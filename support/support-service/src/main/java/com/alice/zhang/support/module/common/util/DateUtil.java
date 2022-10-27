package com.alice.zhang.support.module.common.util;

import cn.hutool.core.util.ObjectUtil;
import com.alice.zhang.support.common.consts.ExceptionConstants;
import com.alice.zhang.support.common.util.BusinessExceptionUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @DateTime 2022/9/21 0021 15:20
 */
public class DateUtil {

    /**
     * 将指定格式的时间字符串转换成日期
     * @author ZhangChenhuang
     * @param dateStr String
     * @param formatStr String
     * @return Date
     */
    public static Date parseDateByFormat(String dateStr, String formatStr) {
        Date date = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            if (ObjectUtil.isNotEmpty(dateStr)) {
                date = format.parse(dateStr);
            }
        } catch (ParseException e) {
            BusinessExceptionUtil.sysEx(ExceptionConstants.EXCEPTION_ILLEGAL_PARAM_POINTER);
            e.printStackTrace();
        }
        return date;
    }

    public static long getSeconds(String start, String end, String formatStr) {
        Date startTime = parseDateByFormat(start, formatStr);
        Date endTime = parseDateByFormat(end, formatStr);
        return (endTime.getTime() - startTime.getTime()) / 1000;
    }

    public static long getMinutes(String start, String end, String formatStr) {
        return getSeconds(start, end, formatStr)/60;
    }

    public static long getHours(String start, String end, String formatStr) {
        return getMinutes(start, end, formatStr)/60;
    }

    public static long getDays(String start, String end, String formatStr) {
        return getHours(start, end, formatStr)/24;
    }

    public static int getMonths(String start, String end, String formatStr) {
        Date startTime = parseDateByFormat(start, formatStr);
        Date endTime = parseDateByFormat(end, formatStr);
        return getYears(start, end, formatStr) * 12 + endTime.getMonth() - startTime.getMonth();
    }

    public static int getYears(String start, String end, String formatStr) {
        Date startTime = parseDateByFormat(start, formatStr);
        Date endTime = parseDateByFormat(end, formatStr);
        return endTime.getYear() - startTime.getYear();
    }

    public static void main(String[] args) {
        long seconds = getSeconds("1997-03-30 00:00:00", null, "yyyy-MM-dd HH:mm:ss");
        long minutes = getMinutes("1997-03-30 00:00:00", null, "yyyy-MM-dd HH:mm:ss");
        long hours = getHours("1997-03-30 00:00:00", null, "yyyy-MM-dd HH:mm:ss");
        long days = getDays("2022-01-01", null, "yyyy-MM-dd");
        int months = getMonths("1997-03-30 00:00:00", null, "yyyy-MM-dd HH:mm:ss");
        int years = getYears("1997-03-30 00:00:00", null, "yyyy-MM-dd HH:mm:ss");
        System.out.println(seconds);
        System.out.println(minutes);
        System.out.println(hours);
        System.out.println(days);
        System.out.println(months);
        System.out.println(years);
    }

}
