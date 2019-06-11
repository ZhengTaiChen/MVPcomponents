package com.wwzs.component.commonsdk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatDate(long time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CANADA);
        return sdf.format(time);
    }

    public static String formatDateAndTime(long time) {
        return formatDate(time, "yyyy-MM-dd HH:mm");
    }

    public static String formatDateToyyyyMMdd(long time) {
        return formatDate(time, "yyyy-MM-dd");

    }

    //获得当天0点时间
    public static long getTimesmorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() / 1000);
    }   //获得当天0点时间

    public static long getTimesmorning1(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis());
    }

    //获得当天24点时间
    public static long getTimesnight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() / 1000);
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @return
     */
    public static long getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Long.valueOf(timestamp.substring(0, length - 3));
        } else {
            return 0;
        }
    }

    public static long getFirstMonthDay() {
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return (cal_1.getTime().getTime() / 1000);
    }

    public static long getFirstWeekDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return (cal.getTime().getTime() / 1000);
    }

    public static long formatToLong(String time, String template) {
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
        try {
            Date d = sdf.parse(time);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            return c.getTimeInMillis();
        } catch (ParseException var7) {
            var7.printStackTrace();
            return 0L;
        }
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(long date1, long date2) {
        return (int) ((date2 - date1) / (1000 * 3600 * 24));
    }

    /**
     * 生成显示时间
     *
     * @param date 时间戳
     * @return
     */
    public static String generateDisplayTime(long date) {
        long nowDate = new Date().getTime();
        long offset = nowDate - date;

        Calendar from = Calendar.getInstance();
        from.setTime(new Date(date));
        Calendar to = Calendar.getInstance();
        to.setTime(new Date());
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int year = toYear - fromYear;
        int month = toMonth - fromMonth;
        String msg;
        if (offset / 1000 < 60) {
            msg = "刚刚";
        } else if (offset / 1000 / 60 < 60) {
            msg = offset / 1000 / 60 + "分钟前";
        } else if (offset / 1000 / 60 / 60 < 24) {
            msg = offset / 1000 / 60 / 60 + "小时前";
        } else if (offset / 1000 / 60 / 60 / 24 < 7) {
            msg = offset / 1000 / 60 / 60 / 24 + "天前";
        } else if (offset / 1000 / 60 / 60 / 24 / 7 < 4) {
            msg = offset / 1000 / 60 / 60 / 24 / 7 + "周前";
        } else if (year == 0 && month > 0) {
            msg = month + "月前";
        } else {
            msg = year + "年前";
        }
        return msg;
    }

}
