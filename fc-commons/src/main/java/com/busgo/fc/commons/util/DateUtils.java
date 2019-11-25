package com.busgo.fc.commons.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author busgo
 * @date 2019-11-18 17:34
 */
public class DateUtils {


    public static final long ONE_DAY_SECONDS = 86400L;
    public static final long ONE_DAY_MILL_SECONDS = 86400000L;
    public static final String chineseDtFormat = "yyyy年MM月dd日";
    public static final String CHINESE_ALL_FORMAT = "yyyy年MM月dd日 HH:mm:ss";
    public static final String shortChineseDtFormat = "M月d日";
    public static final String shortChineseDtFormat_Mdd = "M月dd日";
    public static final String MM_DD = "MM-dd";
    public static final String MMdd = "MM.dd";
    public static final String YYYYMM_Point = "yyyy.MM";
    public static final String YYYYMD_Point = "yyyy.M.d";
    public static final String YYYYMMDD_Point = "yyyy.MM.dd";
    public static final String YYYYMD_Line = "yyyy-M-d";
    public static final String noSecondFormat = "yyyy-MM-dd HH:mm";
    public static final String MM_dd_HHmm = "MM-dd HH:mm";
    public static final String MMdd_HHmm = "MM.dd HH:mm";
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy = "yyyy";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMM = "yyyyMM";
    public static final String yyMMdd = "yyMMdd";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String slashDateFormat = "yyyy/MM/dd";
    public static final String slashDateFormat2 = "MM/dd/yyyy HH:mm:ss";
    public static final String YY_MM_DD = "yy-MM-dd";
    public static final String HH_MM = "HH:mm";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYMMDDHHMM = "yyMMddHHmm";
    public static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    public static final String TIME_SPLIT = ":";
    public static final int YESTERDY = -1;
    public static final int TODAY = 0;
    public static final int TOMORROWDAT = 1;
    public static final int OTHER_DAY = 10000;

    public DateUtils() {
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static int getCurrentYear() {
        return Calendar.getInstance().get(1);
    }

    public static Date getFirstDayOfCurrentYear() {
        Calendar ca = Calendar.getInstance();
        int currentYear = ca.get(1);
        ca.clear();
        ca.set(1, currentYear);
        return ca.getTime();
    }

    public static Date getLastDayOfCurrentYear() {
        Calendar ca = Calendar.getInstance();
        int currentYear = ca.get(1);
        ca.clear();
        ca.set(1, currentYear);
        ca.roll(6, -1);
        ca.set(11, 23);
        ca.set(12, 59);
        ca.set(13, 59);
        return ca.getTime();
    }

    public static DateFormat getNewDateFormat(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        df.setLenient(false);
        return df;
    }

    public static String format(Date date, String format) {
        return date == null ? null : (new SimpleDateFormat(format)).format(date);
    }

    public static Date parseDateNoTime(String sDate, String format) throws ParseException {
        if (StringUtils.isBlank(format)) {
            throw new ParseException("Null format. ", 0);
        } else {
            DateFormat dateFormat = new SimpleDateFormat(format);
            if (sDate != null && sDate.length() >= format.length()) {
                return dateFormat.parse(sDate);
            } else {
                throw new ParseException("length too little", 0);
            }
        }
    }

    public static Date parseDateNewFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        dateFormat.setLenient(false);
        if (sDate != null && sDate.length() == "yyyy-MM-dd HH:mm:ss".length()) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException var4) {
                return null;
            }
        }

        return d;
    }

    public static Date parseDate(String sDate, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date d = null;
        dateFormat.setLenient(false);
        if (sDate != null && sDate.length() == format.length()) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException var5) {
                return null;
            }
        }

        return d;
    }

    public static Date addHours(Date date, long hours) {
        return addMinutes(date, hours * 60L);
    }

    public static Date addMinutes(Date date, long minutes) {
        return addSeconds(date, minutes * 60L);
    }

    public static Date addSeconds(Date date, long secs) {
        return new Date(date.getTime() + secs * 1000L);
    }

    public static Date addMonths(Date date, int months) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(2, months);
        date = cl.getTime();
        return date;
    }

    public static boolean isValidHour(String hourStr) {
        if (!StringUtils.isEmpty(hourStr) && StringUtils.isNumeric(hourStr)) {
            int hour = new Integer(hourStr);
            if (hour >= 0 && hour <= 23) {
                return true;
            }
        }

        return false;
    }

    public static boolean isValidMinuteOrSecond(String str) {
        if (!StringUtils.isEmpty(str) && StringUtils.isNumeric(str)) {
            int hour = new Integer(str);
            if (hour >= 0 && hour <= 59) {
                return true;
            }
        }

        return false;
    }

    public static Date addDays(Date date1, long days) {
        return addSeconds(date1, days * 86400L);
    }

    public static String getNewFormatDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getDateString(date, dateFormat);
    }

    public static String getDateString(Date date, DateFormat dateFormat) {
        return date != null && dateFormat != null ? dateFormat.format(date) : null;
    }

    public static String getChineseDateString(Date date) {
        DateFormat dateFormat = getNewDateFormat("yyyy年MM月dd日");
        return getDateString(date, dateFormat);
    }

    public static long getDiffSeconds(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000L;
    }

    public static long getDiffMinutes(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 60000L;
    }

    public static long getDiffDays(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 86400000L;
    }

    public static long getDiffDays2(Date before, Date after) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(before);
        c1.set(11, 0);
        c1.set(12, 0);
        c1.set(13, 0);
        c1.set(14, 0);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(after);
        c2.set(11, 0);
        c2.set(12, 0);
        c2.set(13, 0);
        c2.set(14, 0);
        return (c2.getTimeInMillis() - c1.getTimeInMillis()) / 86400000L;
    }

    public static String convert(String dateString, DateFormat formatIn, DateFormat formatOut) {
        try {
            Date date = formatIn.parse(dateString);
            return formatOut.format(date);
        } catch (ParseException var4) {
            return "";
        }
    }

    public static boolean dateNotLessThan(String date1, String date2, DateFormat format) {
        try {
            Date d1 = format.parse(date1);
            Date d2 = format.parse(date2);
            return !d1.before(d2);
        } catch (ParseException var5) {
            return false;
        }
    }

    public static String formatTimeRange(Date startDate, Date endDate, String format) {
        if (endDate != null && startDate != null) {
            String rt = null;
            long range = endDate.getTime() - startDate.getTime();
            long day = range / 86400000L;
            long hour = range % 86400000L / 3600000L;
            long minute = range % 3600000L / 60000L;
            if (range < 0L) {
                day = 0L;
                hour = 0L;
                minute = 0L;
            }

            rt = format.replaceAll("dd", String.valueOf(day));
            rt = rt.replaceAll("hh", String.valueOf(hour));
            rt = rt.replaceAll("mm", String.valueOf(minute));
            return rt;
        } else {
            return null;
        }
    }

    public static Date getBeforeDate() {
        return new Date(System.currentTimeMillis() - 86400000L);
    }

    public static Date getDayBegin(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setLenient(false);
        String dateString = df.format(date);

        try {
            return df.parse(dateString);
        } catch (ParseException var4) {
            return date;
        }
    }

    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return calendar.getTime();
    }

    public static boolean dateLessThanNowAddMin(Date date, long min) {
        return addMinutes(date, min).before(new Date());
    }

    public static boolean isBeforeNow(Date date) {
        if (date == null) {
            return false;
        } else {
            return date.compareTo(new Date()) < 0;
        }
    }

    public static Date parseNoSecondFormat(String sDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (sDate != null && sDate.length() >= "yyyy-MM-dd HH:mm".length()) {
            if (!StringUtils.isNumeric(sDate)) {
                throw new ParseException("not all digit", 0);
            } else {
                return dateFormat.parse(sDate);
            }
        } else {
            throw new ParseException("length too little", 0);
        }
    }

    public static Date getFutureDay(int days) {
        try {
            Calendar calendar = GregorianCalendar.getInstance();
            Date date = calendar.getTime();
            calendar.setTime(date);
            calendar.add(5, days);
            return calendar.getTime();
        } catch (Exception var3) {
            return null;
        }
    }

    public static Date getFixDayFutureDay(Date fixDay, int days) {
        try {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(fixDay);
            calendar.add(5, days);
            return calendar.getTime();
        } catch (Exception var3) {
            return null;
        }
    }

    public static Date getFixYearFutureYear(Date date, int years) {
        try {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            calendar.add(1, 1);
            return calendar.getTime();
        } catch (Exception var3) {
            return null;
        }
    }

    public static boolean betweenStartAndEndDate(Date fixDate, Date startDate, Date endDate) {
        if (null == fixDate) {
            fixDate = new Date();
        }

        return fixDate.before(endDate) && fixDate.after(startDate);
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        return getWeekOfDate(dt, weekDays);
    }

    public static String getWeekOfDate(Date dt, String[] weekDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(7) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }

    public static String getWeekNum(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(7) - 1;
        if (w <= 0) {
            w = 7;
        }

        return String.valueOf(w);
    }

    public static int getWeekOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(2);
        cal.setTime(date);
        return cal.get(3);
    }

    public static int getDateAsInt(Date date) {
        String s = (new SimpleDateFormat("yyyyMMdd")).format(date);
        return Integer.parseInt(s);
    }

    public static int getDateAsYYMMddHHmmInt(Date date) {
        String s = (new SimpleDateFormat("yyMMddHHmm")).format(date);
        return Integer.parseInt(s);
    }

    public static int getYearDateAsInt(Date date) {
        String s = (new SimpleDateFormat("yyyy")).format(date);
        return Integer.parseInt(s);
    }

    public static int getYearMonthDateAsInt(Date date) {
        String s = (new SimpleDateFormat("yyyyMM")).format(date);
        return Integer.parseInt(s);
    }

    public static int getYearWeekAsInt(Date date) {
        int yearInt = getYearDateAsInt(date);
        int weekOfYearInt = getWeekOfYear(date);
        String weekOfYearStr;
        if (weekOfYearInt < 10) {
            weekOfYearStr = "0" + weekOfYearInt;
        } else {
            weekOfYearStr = "" + weekOfYearInt;
        }

        String yearWeekStr = yearInt + weekOfYearStr;
        return Integer.valueOf(yearWeekStr);
    }

    public static int getDateAsIntYYMMdd(Date date) {
        String s = (new SimpleDateFormat("yyMMdd")).format(date);
        return Integer.parseInt(s);
    }


    public static int getDateAsIntYYYYMMdd(Date date) {
        String s = (new SimpleDateFormat("yyyyMMdd")).format(date);
        return Integer.parseInt(s);
    }
    public static int date10To8(String date) {
        String s = date.replace("-", "");
        return Integer.parseInt(s);
    }

    public static int HourMin5To4(String date) {
        String s = date.replace(":", "");
        return Integer.parseInt(s);
    }

    public static Date getIntAsDate(Integer date) {
        if (date == null) {
            return null;
        } else {
            try {
                String dateString = date.toString();
                if (dateString.length() != 8) {
                    throw new IllegalArgumentException("date:" + date + " is invalid!");
                } else {
                    return (new SimpleDateFormat("yyyyMMdd")).parse(date.toString());
                }
            } catch (ParseException var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    public static Date getDateWithoutTime(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        String s = fmt.format(date);

        try {
            return fmt.parse(s);
        } catch (ParseException var4) {
            return null;
        }
    }

    public static Date getDateWithFixFormatter(Date date, String formatStr) {
        DateFormat df = new SimpleDateFormat(formatStr);
        df.setLenient(false);
        String dateString = df.format(date);

        try {
            return df.parse(dateString);
        } catch (ParseException var5) {
            return date;
        }
    }

    public static Date getNextTwoAm(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(5, 1);
        c.set(11, 2);
        c.set(12, 0);
        c.set(13, 0);
        return c.getTime();
    }

    public static Integer addDates(Integer date, int days) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

            try {
                Date beginDate = yyyyMMdd.parse(String.valueOf(date));
                return getDateAsInt(addDays(beginDate, (long)days));
            } catch (ParseException var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }

    public static Date getDayLastMill(Date date) {
        if (date == null) {
            return null;
        } else {
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(11, 23);
                calendar.set(12, 59);
                calendar.set(13, 59);
                calendar.set(14, 0);
                return calendar.getTime();
            } catch (Exception var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    public static Date getDayBeginMill(Date date) {
        if (date == null) {
            return null;
        } else {
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(11, 0);
                calendar.set(12, 1);
                calendar.set(13, 0);
                calendar.set(14, 0);
                return calendar.getTime();
            } catch (Exception var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    public static Date getSpecifiedDate(String formatStr, String dateStr) {
        if (dateStr == null) {
            return null;
        } else {
            Date date = null;

            try {
                SimpleDateFormat format = new SimpleDateFormat(formatStr);
                date = format.parse(dateStr);
                return date;
            } catch (Exception var4) {
                return null;
            }
        }
    }

    public static String getSpecDayDisTimes(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            try {
                long disSecond = date1.getTime() - date2.getTime();
                long day = disSecond / 86400000L;
                long hour = disSecond / 3600000L - day * 24L;
                long min = disSecond / 60000L - day * 24L * 60L - hour * 60L;
                long second = disSecond / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
                StringBuilder sb = new StringBuilder();
                return sb.append(decimalFormat.format(hour)).append(":").append(decimalFormat.format(min)).append(":").append(decimalFormat.format(second)).toString();
            } catch (Exception var13) {
                var13.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static int judgmentDay(Date date) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(1) == pre.get(1)) {
            int diffDay = cal.get(6) - pre.get(6);
            switch(diffDay) {
                case -1:
                    return -1;
                case 0:
                    return 0;
                case 1:
                    return 1;
            }
        }

        return 10000;
    }

    public static String getTimeCountdownDesc(Date timeCountdownDate) {
        Date currentTime = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(timeCountdownDate);
        long endTime = c.getTimeInMillis();
        long startTime = currentTime.getTime();
        long midTime = (endTime - startTime) / 1000L;
        int day = (int)(midTime / 60L / 60L / 24L);
        int hour = (int)(midTime / 60L / 60L % 24L);
        int minute = (int)(midTime / 60L % 60L);
        int second = (int)(midTime % 60L);
        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(day).append("天");
        }

        sb.append(hour).append("小时");
        sb.append(minute).append("分钟");
        return sb.toString();
    }

    public static String weekName(int weekNum) {
        String weekName = null;
        if (weekNum == 1) {
            weekName = "周一";
        } else if (weekNum == 2) {
            weekName = "周二";
        } else if (weekNum == 3) {
            weekName = "周三";
        } else if (weekNum == 4) {
            weekName = "周四";
        } else if (weekNum == 5) {
            weekName = "周五";
        } else if (weekNum == 6) {
            weekName = "周六";
        } else if (weekNum == 0) {
            weekName = "周日";
        } else if (weekNum == -1) {
            weekName = "今天";
        }

        return weekName;
    }

    public static String getDateCompareNow(Date date) {
        String newDateStr = format(date, "yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date sDateNew = new Date();

        try {
            sDateNew = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(newDateStr);
        } catch (ParseException var12) {
            var12.printStackTrace();
        }

        calendar.setTime(sDateNew);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(1) - calendar.get(1);
        String strdate = null;
        if (year > 0) {
            strdate = year + "年前";
        } else {
            if (year != 0) {
                return "-1";
            }

            int month = cal.get(2) - calendar.get(2);
            if (month > 0) {
                strdate = month + "月前";
            } else if (month == 0) {
                int day = cal.get(5) - calendar.get(5);
                if (day > 0) {
                    strdate = day + "天前";
                } else if (day == 0) {
                    int hour = cal.get(11) - calendar.get(11);
                    if (hour > 0) {
                        strdate = hour + "小时前";
                    } else if (hour == 0) {
                        int minute = cal.get(12) - calendar.get(12);
                        if (minute > 0) {
                            strdate = minute + "分钟前";
                        } else if (minute == 0) {
                            int seconds = cal.get(13) - calendar.get(13);
                            if (seconds > 0) {
                                strdate = seconds + "秒前";
                            } else {
                                strdate = "刚刚";
                            }
                        }
                    }
                }
            }
        }

        return strdate;
    }

    public static boolean isVaildTimeInerTime(Date nowDate, Date modifyDate, int days) {
        if (modifyDate == null) {
            return true;
        } else {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(modifyDate);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(nowDate);
            return c1.getTimeInMillis() + (long)(86400000 * days) < c2.getTimeInMillis();
        }
    }

    public static boolean isVaildTimeInerByMinutes(Date nowDate, Date modifyDate, int minutes) {
        if (modifyDate == null) {
            return true;
        } else {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(modifyDate);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(nowDate);
            return c1.getTimeInMillis() + (long)('\uea60' * minutes) < c2.getTimeInMillis();
        }
    }
}
