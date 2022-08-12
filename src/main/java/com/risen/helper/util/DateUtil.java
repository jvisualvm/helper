package com.risen.helper.util;


import com.risen.helper.constant.Symbol;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/26 15:32
 */
public class DateUtil {
    public static final String YYYY = "yyyy";
    public static final String MM_DD = "MM-dd";
    public static final String HH_MM = "HH:mm";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MM_DD_HH_MM = "MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS Z";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YYYY_MM_DD_UTC_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHH = "yyyyMMddHH";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    //为了加快速度，提升性能,提前生成对象
    public static ConcurrentHashMap<String, DateTimeFormatter> FORMATMAP = new ConcurrentHashMap<>();

    static {
        DateTimeFormatter DATE_TIME_FORMATTER1 = DateTimeFormatter.ofPattern(YYYY);
        DateTimeFormatter DATE_TIME_FORMATTER2 = DateTimeFormatter.ofPattern(MM_DD);
        DateTimeFormatter DATE_TIME_FORMATTER3 = DateTimeFormatter.ofPattern(HH_MM);
        DateTimeFormatter DATE_TIME_FORMATTER4 = DateTimeFormatter.ofPattern(HH_MM_SS);
        DateTimeFormatter DATE_TIME_FORMATTER5 = DateTimeFormatter.ofPattern(MM_DD_HH_MM_SS);
        DateTimeFormatter DATE_TIME_FORMATTER6 = DateTimeFormatter.ofPattern(YYYY_MM_DD);
        DateTimeFormatter DATE_TIME_FORMATTER7 = DateTimeFormatter.ofPattern(MM_DD_HH_MM);
        DateTimeFormatter DATE_TIME_FORMATTER8 = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM);
        DateTimeFormatter DATE_TIME_FORMATTER9 = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
        DateTimeFormatter DATE_TIME_FORMATTER10 = DateTimeFormatter.ofPattern(YYYY_MM_DD_UTC);
        DateTimeFormatter DATE_TIME_FORMATTER11 = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS_SSS);
        DateTimeFormatter DATE_TIME_FORMATTER12 = DateTimeFormatter.ofPattern(YYYY_MM_DD_UTC_TIME);
        FORMATMAP.put(YYYY, DATE_TIME_FORMATTER1);
        FORMATMAP.put(MM_DD, DATE_TIME_FORMATTER2);
        FORMATMAP.put(HH_MM, DATE_TIME_FORMATTER3);
        FORMATMAP.put(HH_MM_SS, DATE_TIME_FORMATTER4);
        FORMATMAP.put(MM_DD_HH_MM_SS, DATE_TIME_FORMATTER5);
        FORMATMAP.put(YYYY_MM_DD, DATE_TIME_FORMATTER6);
        FORMATMAP.put(MM_DD_HH_MM, DATE_TIME_FORMATTER7);
        FORMATMAP.put(YYYY_MM_DD_HH_MM, DATE_TIME_FORMATTER8);
        FORMATMAP.put(YYYY_MM_DD_HH_MM_SS, DATE_TIME_FORMATTER9);
        FORMATMAP.put(YYYY_MM_DD_UTC, DATE_TIME_FORMATTER10);
        FORMATMAP.put(YYYY_MM_DD_HH_MM_SS_SSS, DATE_TIME_FORMATTER11);
        FORMATMAP.put(YYYY_MM_DD_UTC_TIME, DATE_TIME_FORMATTER12);
    }

    public static boolean isValidDate(String dateStr, String pattern) {
        boolean result = true;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            format.setLenient(false);
            format.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }


    //2022-04-22 22:28
    public static String appendTime(String source, String tail) {
        String[] timeArray = source.split(Symbol.SYMBOL_SPACE);
        StringBuilder builder = new StringBuilder();
        if (PredicateUtil.isOverLimit(timeArray, 1)) {
            builder.append(timeArray[0] + Symbol.SYMBOL_SPACE).append(tail);
        }
        return builder.toString();
    }


    public static String getYyyyMmDdByStr(String source) {
        String[] timeArray = source.split(Symbol.SYMBOL_SPACE);
        StringBuilder builder = new StringBuilder();
        if (PredicateUtil.isOverLimit(timeArray, 1)) {
            builder.append(timeArray[0]);
        }
        return builder.toString();
    }


    public static String cutYYYY(String source) {
        if (StringUtils.isNotEmpty(source)) {
            return source.substring(0, 4);
        }
        return null;
    }


    public static boolean isGt(String source, String target, String pattern) {
        boolean result = true;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            format.setLenient(false);
            Date dateSource = format.parse(source);
            Date dateTarget = format.parse(target);
            result = dateSource.getTime() > dateTarget.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public static boolean isLes(String source, String target, String pattern) {
        boolean result = true;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            format.setLenient(false);
            Date dateSource = format.parse(source);
            Date dateTarget = format.parse(target);
            result = dateSource.getTime() < dateTarget.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }


    public static boolean isBetween(String source, String start, String end, String pattern) {
        boolean result = true;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            format.setLenient(false);
            Date dateSource = format.parse(source);
            Date dateStart = format.parse(start);
            Date dateEnd = format.parse(end);
            result = (dateSource.getTime() >= dateStart.getTime()) && (dateSource.getTime() <= dateEnd.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public static boolean isBetween(Date source, Date start, Date end) {
        return (source.getTime() >= start.getTime()) && (source.getTime() <= end.getTime());
    }

    /**
     * 这针对UTC时间使用
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String getFormatUTCTimeStr(String time, String pattern) {
        SimpleDateFormat formatYYYY = new SimpleDateFormat(pattern);
        Date d = null;
        String timeStr = null;
        try {
            time = getUTC(time);
            SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_UTC);
            d = format.parse(time);
            timeStr = formatYYYY.format(d);
        } catch (Exception e) {
            LogUtil.info(e.getMessage());
        }
        return timeStr;
    }

    public static Date getFormatUTCTimeDate(String time) {
        Date d = null;
        try {
            time = getUTC(time);
            SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_UTC);
            d = format.parse(time);
        } catch (Exception e) {
            LogUtil.info(e.getMessage());
        }
        return d;
    }


    public static String isUTCTime(String time) {
        String localDateStr = null;
        try {
            DateTimeFormatter inputFormatter = FORMATMAP.get(YYYY_MM_DD_UTC_TIME);
            LocalDateTime date = LocalDateTime.parse(time, inputFormatter);
            localDateStr = date.format(FORMATMAP.get(YYYY_MM_DD_HH_MM_SS));
        } catch (Exception e) {
            LogUtil.info(e.getMessage());
        }
        return localDateStr;
    }

    public static Date parseDate(String time, String pattern) {
        Date date = null;
        try {
            DateTimeFormatter format = FORMATMAP.get(pattern);
            LocalDateTime localDateTime = LocalDateTime.parse(time, format);
            Long d = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
            date = new Date(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parseStr2Date(String time, String pattern) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            format.setLenient(false);
            date = format.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String parseDate(Long time, String pattern) {
        String d = null;
        try {
            DateTimeFormatter format = FORMATMAP.get(pattern);
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(time / 1000L, 0, ZoneOffset.ofHours(8));
            d = dateTime.format(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public static Boolean isPatternDate(String time, String pattern) {
        Date d = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            d = format.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ObjectUtils.isNotEmpty(d);
    }

    public static Long parseDateLong(String time, String pattern) {
        Long s = null;
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(time, FORMATMAP.get(pattern));
            s = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    //Calendar.DATE
    public static String getSomeTime(String s, int n, int type) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(type, n);
            return sdf.format(cd.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    private static String getUTC(String time) {
        return time.replace("Z", " UTC");
    }


    public static String getThisMonthFirstDay(int amountMonth, int amountDay) {
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
        cale.add(Calendar.MONTH, amountMonth);
        cale.set(Calendar.DAY_OF_MONTH, amountDay);
        String firstDay = format.format(cale.getTime());
        String startTime = firstDay + " 00:00:00";
        return startTime;
    }

    public static Date getFormatTimeYYYYMMDD(String time, String format) {
        SimpleDateFormat fs = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = fs.parse(time);
        } catch (Exception e) {
            LogUtil.info(e.getMessage());
        }
        return date;
    }


    public static Date reduceDay(Date source, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.add(Calendar.DATE, count);
        return calendar.getTime();
    }


    public static long getZeroTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
}