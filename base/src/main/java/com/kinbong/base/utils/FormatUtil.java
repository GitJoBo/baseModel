package com.kinbong.base.utils;

import android.text.TextUtils;

import com.kinbong.model.util.StringUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtil {
    public static String formatDistance(double meter) {
        DecimalFormat decimalFormat = new DecimalFormat("#.000");
        return decimalFormat.format(meter);
    }

//	public static String formatCo(double  meter){
//		DecimalFormat decimalFormat = new DecimalFormat("#.000");
//		return decimalFormat.format(meter);
//	}

    public static String formatScore(double score) {
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        return decimalFormat.format(score);
    }

    public static String formatMoney(double money) {
        try {
            NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);
            return nf.format(money);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatMoneyStandard(double money) {
        try {
            NumberFormat nf = new DecimalFormat("¥#,###.##");
            nf.setMinimumFractionDigits(2);
            return nf.format(money);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatMoneyStandard(String money) {
        try {
            double d = Double.parseDouble(money);
            NumberFormat nf = new DecimalFormat("¥#,###.##");
            nf.setMinimumFractionDigits(2);
            return nf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatMoneyStandard2(String money) {
        try {
            double d = Double.parseDouble(money);
            NumberFormat nf = new DecimalFormat("#,###.##");
            nf.setMinimumFractionDigits(2);
            return nf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatMoney(String money) {

        try {
            double d = Double.parseDouble(money);
            NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);
            nf.setMinimumFractionDigits(2);
            return nf.format(d);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getFormatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String formatNumber(String text) {
        if (TextUtils.isEmpty(text)) return null;
        int length = text.length();
        if (length < 8) return text;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i < 4 || i >= length - 4) {
                sb.append(text.charAt(i));
            } else {
                sb.append("*");
            }
        }
        return sb.toString();
    }
//	public static String formatDiskCache(){
//
//		long length = ImageLoader.getInstance().getDiskCache().
//		String s = Formatter.formatFileSize(BaseApplication.getContext(), length);
//		return s;
//	}

    /**
     * 保留小数点两位
     *
     * @param string
     * @return
     */
    public static String getDoubleString(String string) {
        try {
            double temp;
            temp = Double.parseDouble(string);
            String result = String.format("%.2f", temp);
            return result;
        } catch (Exception e) {
        }
        return "";
    }


}
