package com.kinbong.model.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/9/28.
 */

public class DateUtil {

    @SuppressLint("SimpleDateFormat")
    public static String longtimeToDate(long time) {
        String dateStr = null;
        try {
            Date now = new Date(time * 1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            dateStr = dateFormat.format(now);
        } catch (Exception e) {
            e.printStackTrace();
            dateStr="0000-00-00 00:00:00";
        }
        return dateStr;
    }

    @SuppressLint("SimpleDateFormat")
    public static String longtimeToDate2(long time) {
        String dateStr = null;
        try {
            Date now = new Date(time * 1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm");// 可以方便地修改日期格式
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            dateStr = dateFormat.format(now);
        } catch (Exception e) {
            e.printStackTrace();
            dateStr="0000-00-00 00:00";
        }
        return dateStr;
    }

    @SuppressLint("SimpleDateFormat")
    public static String longtimeToDate3(long time) {
        String dateStr = null;
        try {
            Date now = new Date(time * 1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd ");// 可以方便地修改日期格式
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            dateStr = dateFormat.format(now);
        } catch (Exception e) {
            e.printStackTrace();
            dateStr="0000-00-00 ";
        }
        return dateStr;
    }

    /**
     * 修改时间戳转化时间
     * by黄海杰 at:2015年7月27日 15:44:16
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String longtimeToDayDate(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");// 可以方便地修改日期格式
        String dateStr = dateFormat.format(new Date(time));
        return dateStr;
    }
    public static String longtimeToDayDate(long time,String patterd) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(patterd);// 可以方便地修改日期格式
        String dateStr = dateFormat.format(new Date(time));
        return dateStr;
    }

    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String currentTime = sdf.format(date);
        return currentTime;
    }

//    public static String getDate(Date date,String pattern){
//        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);// 可以方便地修改日期格式
//       return dateFormat.format(date);
//    }

//    @SuppressLint("SimpleDateFormat")
//    public static String longtimeToDate(long time,String patterd) {
//        String dateStr = null;
//        try {
//            Date now = new Date(time * 1000);
//            SimpleDateFormat dateFormat = new SimpleDateFormat(
//                    patterd);// 可以方便地修改日期格式
//            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//            dateStr = dateFormat.format(now);
//        } catch (Exception e) {
//            e.printStackTrace();
//            dateStr=patterd;
//        }
//        return dateStr;
//    }

    @SuppressLint("SimpleDateFormat")
    public static String longtimeToDateYMD(long time) {
        Date now = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String dateStr = dateFormat.format(now);
        return dateStr;
    }

    @SuppressLint("SimpleDateFormat")
    public static String longtimeToDateYMDHM(long time) {
        Date now = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM");// 可以方便地修改日期格式
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String dateStr = dateFormat.format(now);
        return dateStr;
    }


    /**
     * 根据yyyy-MM-dd HH:mm:ss格式时间字符串转为long型时间戳
     *
     * @param dateStr
     * @return date long
     * by:Hankkin at:2015年6月25日 17:38:25 修改时区设置
     */
    public static long stringDateToLong(String dateStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date==null){
            return 0;
        }
        return date.getTime();
    }
    public static long stringDateToLong2(String dateStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date==null){
            return 0;
        }
        return date.getTime();
    }


    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     * 修改为向下取整
     *
     * @param dateTime 时间戳
     * @return
     */
    public static String getStandardDate(long dateTime) {

        StringBuffer sb = new StringBuffer();
//		long t = Long.parseLong(timeStr);
//		long time = System.currentTimeMillis() - (t*1000);
        long time = System.currentTimeMillis() - (dateTime);
        long mill = (long) Math.floor(time / 1000);//秒前

        long minute = (long) Math.floor(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.floor(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.floor(time / 24 / 60 / 60 / 1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

    /**
     * 根据时间戳的差获取时间差
     * 修改超过一天的显示时间
     *
     * @param dateTime
     * @return
     */
    public static String getDateAgo(long dateTime) {

        String days = null;
//		long t = Long.parseLong(timeStr);
//		long time = System.currentTimeMillis() - (t*1000);
        long timeInterval = (System.currentTimeMillis() - (dateTime)) / 1000;


        if (timeInterval < 60) {
            days = "1分钟前";
        } else if (timeInterval < 3600) {
            days = "" + (int) Math.round(timeInterval / 60) + "分钟内";
        } else if (timeInterval < 86400) {
            if (timeInterval % 3600 > 1800) {
                days = "" + (int) Math.round((timeInterval / 3600) + 1) + "小时内";
            } else {
                days = "" + (int) Math.round((timeInterval / 3600)) + "小时内";
            }
        }
//		else if (timeInterval<2592000){
//			days = ""+(int)Math.floor(timeInterval/86400)+"天前";
//		}
//		else if (timeInterval<31536000){
//			days = ""+(int)Math.floor(timeInterval/2592000)+"个月前";
//		}
//		else {
//			days = ""+(int)Math.floor(timeInterval/31536000)+"年前";
//		}
        else {
            days = longtimeToDayDate(dateTime);
        }
        return days;
    }

    /**
     * 把yyyymmdd转成yyyy-MM-dd格式
     * @param str
     * @param str1 yyyymmdd
     * @param str2 yyyy-MM-dd
     * @return
     */
    public static String formatDate(String str,String str1,String str2){
//        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat sf2 =new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf1 = new SimpleDateFormat(str1);
        SimpleDateFormat sf2 =new SimpleDateFormat(str2);
        String sfstr = "";
        try {
            sfstr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sfstr;
    }

}
