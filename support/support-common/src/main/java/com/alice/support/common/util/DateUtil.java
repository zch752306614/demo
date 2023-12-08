package com.alice.support.common.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alice.support.common.consts.SysConstants;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 日期工具类
 * @DateTime 2023/11/30 13:49
 */
@Slf4j
public class DateUtil {

    private DateUtil() {
    }

    public static Date currentDate() {
        // 转换为中国时区
        TimeZone time = TimeZone.getTimeZone("Etc/GMT-8");
        TimeZone.setDefault(time);
        return new Date();
    }

    public static Date transferDate(Date date, String pattern) {
        if (ObjectUtil.isEmpty(pattern)) {
            pattern = SysConstants.DEFAULT_DATE_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String format = sdf.format(date);
        Date parse = null;
        try {
            parse = sdf.parse(format);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return parse;
    }

    public static Date defaultFormatDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(SysConstants.DEFAULT_DATE_FORMAT);
        String format = sdf.format(new Date());
        Date parse = null;
        try {
            parse = sdf.parse(format);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return parse;
    }

    /**
     * 将String 转成Date
     *
     * @param date 日期
     * @return pattern 格式
     */
    public static Date transferDate(String date, String pattern) {
        if (ObjectUtil.isEmpty(pattern)) {
            pattern = SysConstants.DEFAULT_DATE_FORMAT;
        }
        SimpleDateFormat ft = new SimpleDateFormat(pattern);
        Date newDate = null;
        try {
            newDate = ft.parse(date);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return newDate;
    }

    public static String transferDateToString(Date date, String pattern) {
        if (ObjectUtil.isEmpty(pattern)) {
            pattern = SysConstants.DEFAULT_DATE_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String defaultFormatDateToString() {
        SimpleDateFormat sdf = new SimpleDateFormat(SysConstants.DEFAULT_DATE_FORMAT);
        return sdf.format(new Date());
    }

    /**
     * 获取某一年月的下一个月
     *
     * @param currMonth 年月
     */
    public static String getNextMonth(String currMonth) {
        return getNextMonth(currMonth, 1, null);
    }

    /**
     * 获取某一年月的上一个月
     *
     * @param currMonth 年月
     */
    public static String getLastMonth(String currMonth) {
        return getNextMonth(currMonth, -1, null);
    }

    /**
     * 获取某一年的下一年
     *
     * @param currYear 年份
     */
    public static String getNextYear(String currYear) {
        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(new SimpleDateFormat("yyyy").parse(currYear));
        } catch (ParseException e) {
            log.warn(e.getMessage());
        }
        ca.add(Calendar.YEAR, 1);
        Date nextMonth = ca.getTime();
        return transferDateToString(nextMonth, "yyyy");
    }

    /**
     * 将年月区间转化为集合
     *
     * @param beginYM 开始年月
     * @param endYM   终止年月
     */
    public static List<Long> convertYMList(Long beginYM, Long endYM) {
        List<Long> results = new ArrayList<>();
        try {
            Date startDate = new SimpleDateFormat("yyyyMM").parse(beginYM.toString());
            Date endDate = new SimpleDateFormat("yyyyMM").parse(endYM.toString());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            // 获取开始年份和开始月份
            int startYear = calendar.get(Calendar.YEAR);
            int startMonth = calendar.get(Calendar.MONTH);
            // 获取结束年份和结束月份
            calendar.setTime(endDate);
            int endYear = calendar.get(Calendar.YEAR);
            int endMonth = calendar.get(Calendar.MONTH);
            for (int i = startYear; i <= endYear; i++) {
                String date;
                if (startYear == endYear) {
                    for (int j = startMonth; j <= endMonth; j++) {
                        if (j < 9) {
                            date = i + "0" + (j + 1);
                        } else {
                            date = i + SysConstants.EMPTY + (j + 1);
                        }
                        results.add(Long.parseLong(date));
                    }

                } else {
                    if (i == startYear) {
                        for (int j = startMonth; j < 12; j++) {
                            if (j < 9) {
                                date = i + "0" + (j + 1);
                            } else {
                                date = i + SysConstants.EMPTY + (j + 1);
                            }
                            results.add(Long.parseLong(date));
                        }
                    } else if (i == endYear) {
                        for (int j = 0; j <= endMonth; j++) {
                            if (j < 9) {
                                date = i + "0" + (j + 1);
                            } else {
                                date = i + SysConstants.EMPTY + (j + 1);
                            }
                            results.add(Long.parseLong(date));
                        }
                    } else {
                        for (int j = 0; j < 12; j++) {
                            if (j < 9) {
                                date = i + "0" + (j + 1);
                            } else {
                                date = i + SysConstants.EMPTY + (j + 1);
                            }
                            results.add(Long.parseLong(date));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return results;
    }

    /**
     * 获取某年月n月之后的年月
     *
     * @param n 月数
     */
    public static String getNextMonth(String currMonth, int n) {
        return getNextMonth(currMonth, n, null);
    }

    public static String getNextMonth(String currMonth, int n, String pattern) {
        if (CharSequenceUtil.isBlank(pattern)) {
            pattern = "yyyyMM";
        }
        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(new SimpleDateFormat(pattern).parse(currMonth));
        } catch (ParseException e) {
            log.warn(e.getMessage());
        }
        ca.add(Calendar.MONTH, n);
        Date nextMonth = ca.getTime();
        return transferDateToString(nextMonth, pattern);
    }

    /**
     * 获取某月的最后一天
     *
     * @param year 年份
     * @return month 月份
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(cal.getTime());
    }

    /**
     * 获取两个月份相差的月数
     *
     * @param monthFirst  月份1
     * @param monthSecond 月份2
     */
    public static int getMonthsBetweenForLong(String monthFirst, String monthSecond) {
        int var9;
        if (monthFirst.length() == 6 && monthSecond.length() == 6) {
            int var3 = Integer.parseInt(monthFirst);
            int var4 = Integer.parseInt(monthSecond);
            if (var3 < var4) {
                var9 = -2;
            } else {
                int var5 = Integer.parseInt(monthFirst.substring(0, 4));
                int var6 = Integer.parseInt(monthFirst.substring(4, 6));
                int var7 = Integer.parseInt(monthSecond.substring(0, 4));
                int var8 = Integer.parseInt(monthSecond.substring(4, 6));
                var9 = (var5 - var7) * 12 + (var6 - var8);
            }
        } else {
            var9 = -1;
        }

        return Math.abs(var9);
    }

    public static String getMonthBetween(String var0, String var1) {
        try {
            String var4;
            if (!SysConstants.EMPTY.equals(var1) && var0.length() == 6 && var1.length() == 6) {
                int var2 = Integer.parseInt(var0.substring(0, 4)) * 12 + Integer.parseInt(var0.substring(4, 6));
                int var3 = Integer.parseInt(var1.substring(0, 4)) * 12 + Integer.parseInt(var1.substring(4, 6));
                var4 = String.valueOf(var2 - var3);
            } else {
                var4 = SysConstants.EMPTY;
            }

            return var4;
        } catch (Exception var5) {
            var5.printStackTrace();
            return "0";
        }
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate 日期
     */
    public static Date strToDateLong(String strDate) throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMM");
        return ft.parse(strDate);
    }

    public static Date getNextDate(String date, int n, String pattern) {
        if (CharSequenceUtil.isBlank(pattern)) {
            pattern = "yyyyMMdd";
        }
        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(new SimpleDateFormat(pattern).parse(date));
        } catch (ParseException e) {
            log.warn(e.getMessage());
        }
        ca.add(Calendar.DATE, n);
        return ca.getTime();
    }

    /**
     * @param startDateOne 第一个时间段的开始时间
     * @param endDateOne   第一个时间段的结束时间
     * @param startDateTwo 第二个时间段的开始时间
     * @param endDateTwo   第二个时间段的结束时间
     */
    public static Boolean checkIntersection(Date startDateOne, Date endDateOne, Date startDateTwo, Date endDateTwo) {
        Date maxStartDate = startDateOne;
        if (maxStartDate.before(startDateTwo)) {
            maxStartDate = startDateTwo;
        }
        Date minEndDate = endDateOne;
        if (endDateTwo.before(minEndDate)) {
            minEndDate = endDateTwo;
        }
        return maxStartDate.before(minEndDate) || (maxStartDate.getTime() == minEndDate.getTime());
    }

    /**
     * 校验传入的时间格式
     *
     * @param date 日期
     */
    public static boolean validateDate(Long date) {
        return date.toString().length() != 8;
    }

    /**
     * 校验传入的时间格式
     *
     * @param date 日期
     */
    public static boolean validateYearMonth(String date) {
        return date.length() != 6;
    }

    /**
     * 将时间字符串格式化为指定格式的时间字符串
     *
     * @param oldDate   转换的时间
     * @param oldFormat 旧的格式
     * @param newFormat 新的格式
     */
    public static String strDateToStrDateByFormat(String oldDate, String oldFormat, String newFormat) {
        SimpleDateFormat oldSdf = new SimpleDateFormat(oldFormat);
        SimpleDateFormat newSdf = new SimpleDateFormat(newFormat);
        String newDate = SysConstants.EMPTY;
        try {
            Date date = oldSdf.parse(oldDate);
            newDate = newSdf.format(date);
        } catch (ParseException e) {
            log.warn(e.getMessage());
        }
        return newDate;
    }

}
