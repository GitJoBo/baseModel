package com.kinbong.model.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.kinbong.model.ModelApp;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;


/**
 * Created by Administrator on 2016/3/1.
 */
public class DevicesUtil {


    /**
     * 获得屏幕高度
     *
     * @return
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) ModelApp.sContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @return
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) ModelApp.sContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    public static String getDeviceId() {
        final TelephonyManager tm = (TelephonyManager)ModelApp.sContext.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(ModelApp.sContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    /**
     * 获取屏幕密度
     *
     * @return
     */
    public static float getScreenDensity() {
        return ModelApp.sContext.getResources().getDisplayMetrics().density;
    }
    public static boolean chcekThread() {
        return Looper.myLooper()==Looper.getMainLooper();

    }



    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
//    public static int px2dp( float pxValue) {
//        final float scale = getDisplayMetrics().density;
//        return (int) (pxValue / scale + 0.5f);
//    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(float spValue) {
        final float fontScale = getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private static DisplayMetrics getDisplayMetrics() {
        return ModelApp.sContext.getResources().getDisplayMetrics();
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
//    public static int px2sp(float pxValue) {
//        final float fontScale =getDisplayMetrics().scaledDensity;
//        return (int) (pxValue / fontScale + 0.5f);
//    }

    /**
     * 各种单位转换
     * <p>该方法存在于TypedValue</p>
     *
     * @param unit    单位
     * @param value   值
     * @return 转换结果
     */
    public static float applyDimension(int unit, float value) {
        DisplayMetrics metrics = getDisplayMetrics();
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }


    public static int getVersionCode() {
        int versionCode = 0;
        try {
            versionCode =  ModelApp.sContext
                    .getPackageManager()
                    .getPackageInfo( ModelApp.sContext.getPackageName(),
                            0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 获得状态栏的高度
     *
     * @return
     */
    public static int getStatusHeight() {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = ModelApp.sContext.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


    /**
     * 隐藏软键盘
     */
    public static void hideSoftInputMethod(View v) {
        WeakReference<View> wr = new WeakReference<>(v);
        View view = wr.get();
        if(view==null){
            return;
        }
        if(!isSoftInputOPen())return;
        // 隐藏虚拟键盘
        InputMethodManager inputmanger = (InputMethodManager) ModelApp.sContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public static boolean isSoftInputOPen(){
        /**获取输入法打开的状态**/
        InputMethodManager imm = (InputMethodManager)ModelApp.sContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        //isOpen若返回true，则表示输入法打开，反之则关闭
        return imm.isActive();

    }

    /**
     * 此方法只是关闭软键盘和activity
     * @param context
     */
    public static void closeKeybord(Activity context) {
        InputMethodManager imm = (InputMethodManager)context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&& context.getCurrentFocus() !=null){
            if (context.getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        context.finish();
    }

    /**
     * 切换软件盘 显示隐藏
     */
    public static void switchSoftInputMethod() {
        // 方法一(如果输入法在窗口上已经显示，则隐藏，反之则显示)
        InputMethodManager iMM = (InputMethodManager) ModelApp.sContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        iMM.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public static void showSoftKeyboard(View view) {
        ((InputMethodManager) ModelApp.sContext.getSystemService(
                Context.INPUT_METHOD_SERVICE)).showSoftInput(view,
                InputMethodManager.SHOW_FORCED);
    }

    public static String getVersionName() {
        String name = "";
        try {
            name =  ModelApp.sContext
                    .getPackageManager()
                    .getPackageInfo( ModelApp.sContext.getPackageName(),
                            0).versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            name = "";
        }
        return name;
    }



    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }



    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    public static void fixInputMethodManagerLeak(Context destContext) {

        InputMethodManager imm = (InputMethodManager) ModelApp.sContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        String [] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0;i < arr.length;i ++) {
            String param = arr[i];
            try{
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了

                        break;
                    }
                }
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }

    public static void systemKeyDisabled(EditText editText){

        Context context = editText.getContext();
        if(context instanceof Activity)
            ((Activity)context).getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    public final static boolean isThanM() {
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.M;
    }
}
