package com.kinbong.model.util;


import android.content.ClipboardManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import com.kinbong.model.ModelApp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Administrator on 2016/3/1.
 */
public class StringUtil {

    /**
     * 字符串拼接
     * @param ss
     * @return
     */
    public final static String splice(String... ss){
        StringBuilder sb = new StringBuilder();
        for (String s:ss){
            sb.append(s);
        }
        return sb.toString();
    }
    /**
     * 实现文本复制功能 add by lif
     *
     * @param content
     */
    public static void copy(String content) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) ModelApp.sContext
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());

    }

    /**
     * 实现粘贴功能 add by lif
     *
     * @return
     */
    public static String paste() {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) ModelApp.sContext
                .getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }


    /**
     * 验证是否手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1]\\d{10}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 中文识别
     */
    public static boolean hasChinese(String source) {
        String reg_charset = "([\\u4E00-\\u9FA5]*+)";
        Pattern p = Pattern.compile(reg_charset);
        Matcher m = p.matcher(source);
        boolean hasChinese = false;
        while (m.find()) {
            if (!"".equals(m.group(1))) {
                hasChinese = true;
            }
        }
        return hasChinese;
    }

    /**
     * 用户名规则判断
     *
     * @param uname
     * @return
     */
    public static boolean isAccountStandard(String uname) {
        Pattern p = Pattern.compile("[A-Za-z0-9_]+");
        Matcher m = p.matcher(uname);
        return m.matches();
    }

    // java 合并两个byte数组
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    /**
     * 获取手机号
     * by Hankkin
     *
     * @param context
     * @return
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getLine1Number();
    }

    /**
     * 去掉字符空格，换行符等
     *
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public final static String delSpace(String str) {
        if (str != null) {
            str = str.replaceAll("\r", "");
            str = str.replaceAll("\n", "");
            str = str.replaceAll(" ", "");
        }
        return str;
    }

    /**
     * 将JSON数据中的/n 替换 为Android换行的 /n
     */
    public final static String JsonFilter(String jsonstr) {
        jsonstr = jsonstr.replace("\\n", "\n");
        return jsonstr;
    }

    public static String getResStr(int id) {
        return ModelApp.sContext.getString(id);
    }

    public final static String getFormatJson(String... value){
        if(value==null||value.length==0)return "";
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int length = value.length;
        for (int i=0;i<length;i++){
            String s = value[i];
            sb.append("\"");
            if(!(TextUtils.isEmpty(s)||TextUtils.equals(s,"null"))){
                sb.append(s);
            }
            sb.append("\"");
            if((i+1)%2!=0){
               sb.append(":") ;
            }else {
                if(length>=3&&i!=length-1)
                sb.append(",");
            }
        }
        sb.append("}");
        String str = sb.toString();
        LogUtil.d(str);
        return str;
    }

    /**
     * 正则规划，拿指定位置的段落
     * @param str
     * @param tag 正则规则
     * @param index 指定位置
     * @return
     */
    public static String getBothEnds(String str,String tag,int index){
        String[]  strs=str.split(tag);
        /*for(int i=0,len=strs.length;i<len;i++){
            System.out.println(strs[i].toString());
        }*/
        return strs[index];
    }

    public static String getText(String name) {
        return TextUtils.isEmpty(name)?"":name;
    }

    /**
     * 掉此方法输入所要转换的时间输入例如（"2014年06月14日16时09分00秒"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String getData(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String getTimeStamp(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14  16:09:00"）
     *
     * @param time
     * @return
     */
    public static String getTimedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    /**
     * String 转int
     * @param str
     * @return
     */
    public static int getInt(String str){
        int a = 0;
        try {
            a = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return a;
    }
}
