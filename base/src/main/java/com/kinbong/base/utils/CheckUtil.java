package com.kinbong.base.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @desc:一些用到的验证.
 * 
 */

public class CheckUtil {

	/*
	 * 验证手机号 ,
	 */
	public static boolean isMobileNO(String mobiles) {
		if(TextUtils.isEmpty(mobiles)||mobiles.length()!=11)return false;
		String regExp = "^1\\d{10}";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(mobiles);
		boolean ismobi = m.find();
		return ismobi;
	}
	/**
	 * 验证邮箱.
	 */
	public static boolean isEmail(String strEmail) {
		String regExp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}

	/**
	 * 判断是否全是数字
	 * 
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

}
