package com.qianyu.communicate.utils;

import org.haitao.common.utils.AppLog;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class NumberUtils {

    //value 被除数  scale保留几位小鼠  one除以多少
    public static String roundInt(int value) {
        if (value < 10000) {
            return value + "";
        }
        BigDecimal b = 0 == value ? new BigDecimal("0") : new BigDecimal(Integer.toString(value));
        BigDecimal one = new BigDecimal("10000");
        return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue() + "万";
    }

//    public static String roundInt1(int value) {
//        if (value < 10000) {
//            return value + "";
//        }
//        BigDecimal b = 0 == value ? new BigDecimal("0") : new BigDecimal(Integer.toString(value));
//        BigDecimal one = new BigDecimal("10000");
//        return b.divide(one, 1, BigDecimal.ROUND_HALF_UP).doubleValue() + "万";
//    }

    //value 被除数  scale保留几位小鼠  one除以多少
    public static String rounDouble(double value) {
        if (value < 10000) {
            return value + "";
        }
        BigDecimal b = 0 == value ? new BigDecimal("0.00") : new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("10000");
        return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue() + "万";
    }

    public static String rounLong(long value) {
        if (value < 10000) {
            return value + "";
        }
        BigDecimal b = 0 == value ? new BigDecimal("0.00") : new BigDecimal(Long.toString(value));
        BigDecimal one = new BigDecimal("10000");
        return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).longValue() + "万";
    }

    public static String rounDouble_(double value) {
//        if (value < 1000) {
//            return value + "m";
//        }
        BigDecimal b = 0 == value ? new BigDecimal("0.00") : new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("1000");
        return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue() + "Km";
    }

    //value1 被除数  scale保留几位小鼠  one除以多少
    public static String roundPercent(long value1, long value2) {
        if (value1 <= 0 || value2 <= 0) {
            return "0.0%";
        }
        BigDecimal b = 0 == value1 ? new BigDecimal("0.00") : new BigDecimal(Double.toString(value1));
        BigDecimal one = new BigDecimal("" + value2);
        return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue() * 100 + "%";
    }

    //value 被除数  scale保留几位小鼠  one除以多少
    public static String roundInt_(int value) {
        if (value < 1024 * 1024) {
            BigDecimal b = 0 == value ? new BigDecimal("0") : new BigDecimal(Integer.toString(value));
            BigDecimal one = new BigDecimal(1024 + "");
            return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue() + "KB";
        }
        if (value < 1024) {
            return value + "B";
        }
        BigDecimal b = 0 == value ? new BigDecimal("0") : new BigDecimal(Integer.toString(value));
        BigDecimal one = new BigDecimal(1024 * 1024 + "");
        return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue() + "M";
    }

    // 返回单位是米
    private static double EARTH_RADIUS = 6378.137;

    public static String getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        AppLog.e(lat1 + "," + lng1 + "=======" + rounDouble_(s) + "=======" + lat2 + "," + lng2);
        return rounDouble_(s);
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}
