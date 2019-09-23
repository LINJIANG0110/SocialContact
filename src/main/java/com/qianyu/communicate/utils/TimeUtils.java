package com.qianyu.communicate.utils;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint({"SimpleDateFormat"})
public class TimeUtils {
    public static final String FORMAT_YEAR = "yyyy";
    public static final String FORMAT_MONTH_DAY = "MM月dd日";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm";
    public static final String FORMAT_MONTH_DAY_TIME = "MM月dd日  hh:mm";
    public static final String FORMAT_MONTH_DAY_TIME_EN = "MM-dd hh:mm";
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DATE1_TIME = "yyyy/MM/dd HH:mm";
    public static final String FORMAT_DATE_TIME_SECOND = "yyyy_MM_dd_HH_mm_ss";
    public static final String FORMAT_DATE_SECOND = "MM/dd/yyyy HH:mm:ss";
    private static SimpleDateFormat sdf = new SimpleDateFormat();
    private static final int YEAR = 31536000;
    private static final int MONTH = 2592000;
    private static final int DAY = 86400;
    private static final int HOUR = 3600;
    private static final int MINUTE = 60;

    public TimeUtils() {
    }

    public static String getDescriptionTimeFromTimestamp(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000L;
        System.out.println("timeGap: " + timeGap);
        String timeStr = null;
        if (timeGap > 31536000L) {
            timeStr = timeGap / 31536000L + "年前";
        } else if (timeGap > 2592000L) {
            timeStr = timeGap / 2592000L + "个月前";
        } else if (timeGap > 86400L) {
            timeStr = timeGap / 86400L + "天前";
        } else if (timeGap > 3600L) {
            timeStr = timeGap / 3600L + "小时前";
        } else if (timeGap > 60L) {
            timeStr = timeGap / 60L + "分钟前";
        } else {
            timeStr = "刚刚";
        }
        if(timestamp<=0){
            timeStr="一天前";
        }
        return timeStr;
    }

    public static Date longToDate(long currentTime) {
        Date dateOld = new Date(currentTime);
        return dateOld;
    }

    public static String longToString(long currentTime, String formatType) {
        String strTime = "";
        Date date = longToDate(currentTime);
        strTime = dateToString(date, formatType);
        return strTime;
    }

    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType);
        if (date == null) {
            return 0L;
        } else {
            long currentTime = dateToLong(date);
            return currentTime;
        }
    }

    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;

        try {
            date = formatter.parse(strTime);
        } catch (ParseException var5) {
            Log.e("date error", var5.toString());
        }

        return date;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static String dateToString(Date data, String formatType) {
        return (new SimpleDateFormat(formatType)).format(data);
    }

    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(time));
    }

    public static String getTime2(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return format.format(new Date(time));
    }

    public static String getTime_(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(time));
    }

    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date(time));
    }

    public static String getZoneTime(long timesamp) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(timesamp);
        int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));
        switch (temp) {
            case 0:
                result = "今天 ";
                break;
            case 1:
                result = "昨天 ";
                break;
            case 2:
                result = "前天 ";
                break;
            default:
                SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                result = format.format(new Date(timesamp));
        }

        return result;
    }

    public static String getCurrentTime(String format) {
        if (format != null && !format.trim().equals("")) {
            sdf.applyPattern(format);
        } else {
            sdf.applyPattern("yyyy-MM-dd HH:mm");
        }

        return sdf.format(new Date());
    }

    public static boolean isAfter(Date olddate, Date newDate, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(olddate);
        cal.set(11, cal.get(11) + hours);
        Date da = cal.getTime();
        return newDate.after(da);
    }

    public static int getWeek(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        int weekZh = 0;
        int week = now.get(7);
        if (week == 1) {
            weekZh = 7;
        } else {
            --week;
        }

        return weekZh;
    }

    public static String getWeekZh(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        String weekZh = "";
        int week = now.get(7);
        switch (week) {
            case 1:
                weekZh = "星期日";
                break;
            case 2:
                weekZh = "星期一";
                break;
            case 3:
                weekZh = "星期二";
                break;
            case 4:
                weekZh = "星期三";
                break;
            case 5:
                weekZh = "星期四";
                break;
            case 6:
                weekZh = "星期五";
                break;
            case 7:
                weekZh = "星期六";
        }

        return weekZh;
    }

    public static String[] getDateAfter(Date d, int day, String formatType) {
        if (day < 0) {
            return null;
        } else {
            String[] str = new String[day];

            for (int i = 0; i < day; ++i) {
                str[i] = dateToString(getDateAfter(d, i), formatType);
            }
            return str;
        }
    }

    public static Date getDateAfter(Date d, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(5, cal.get(5) + day);
        return cal.getTime();
    }

    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            smdate = sdf.parse(sdf.format(smdate));
        } catch (ParseException var11) {
            var11.printStackTrace();
        }

        try {
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException var10) {
            var10.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / 86400000L;
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static int daysBetween(long smdate, long bdate) {
        return daysBetween(longToDate(smdate), longToDate(bdate));
    }

    // 根据时间戳计算年龄
    public static int getAgeFromBirthTime(long birthTimeLong) {
        Date date = new Date(birthTimeLong);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String birthTimeString = format.format(date);
        return getAgeFromBirthTime(birthTimeString);
    }

    // 根据年月日计算年龄,birthTimeString:"1994-11-14"
    public static int getAgeFromBirthTime(String birthTimeString) {
        if (TextUtils.isEmpty(birthTimeString)) {
            return 0;
        }
        // 先截取到字符串中的年、月、日
        String strs[] = birthTimeString.trim().split("-");
        int selectYear = Integer.parseInt(strs[0]);
        int selectMonth = Integer.parseInt(strs.length < 2 ? "1" : strs[1]);
        int selectDay = Integer.parseInt(strs.length < 3 ? "1" : strs[2]);
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        int yearMinus = yearNow - selectYear;
        int monthMinus = monthNow - selectMonth;
        int dayMinus = dayNow - selectDay;

        int age = yearMinus;// 先大致赋值
        if (yearMinus < 0) {// 选了未来的年份
            age = 0;
        } else if (yearMinus == 0) {// 同年的，要么为1，要么为0
            if (monthMinus < 0) {// 选了未来的月份
                age = 0;
            } else if (monthMinus == 0) {// 同月份的
                if (dayMinus < 0) {// 选了未来的日期
                    age = 0;
                } else if (dayMinus >= 0) {
                    age = 1;
                }
            } else if (monthMinus > 0) {
                age = 1;
            }
        } else if (yearMinus > 0) {
            if (monthMinus < 0) {// 当前月>生日月
            } else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄
                if (dayMinus < 0) {
                } else if (dayMinus >= 0) {
                    age = age + 1;
                }
            } else if (monthMinus > 0) {
                age = age + 1;
            }
        }
        return age;
    }

    public static int getYearByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String year = date.substring(0, 4);
        return Integer.parseInt(year);
    }

    public static int getMonthByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String month = date.substring(5, 7);
        return Integer.parseInt(month);
    }

    public static int getDayByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String day = date.substring(8, 10);
        return Integer.parseInt(day);
    }

    public static int getHourByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String hour = date.substring(11, 13);
        return Integer.parseInt(hour);
    }

    public static int getMinuteByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String minute = date.substring(14, 16);
        return Integer.parseInt(minute);
    }

    public static String timeStampToDate(long timeStamp) {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    public static String secondToHMS(long seconds) {
        long temp = 0;
        StringBuffer sb = new StringBuffer();
        temp = seconds / 3600;
        sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");

        temp = seconds % 3600 / 60;
        sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");

        temp = seconds % 3600 % 60;
        sb.append((temp < 10) ? "0" + temp : "" + temp);
        return sb.toString();
    }

    /**
     * 将毫秒转化成指定格式的时间
     *
     * @param type
     * @param millisecond
     * @return
     */
    public static String transitionTime(String type, long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        Date date = new Date(millisecond);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
    /**
     * 将毫秒转时分秒
     *
     * @param time
     * @return
     */
    public static String getHMSTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * 获取精确到秒的时间戳
     * @param date
     * @return
     */
    public static int getSecondTimestampTwo(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime()/1000);
        return Integer.valueOf(timestamp);
    }

}
