package com.kinbong.model.util;


import android.content.pm.ApplicationInfo;

import com.kinbong.model.ModelApp;
import com.kinbong.model.bean.Result;
import com.orhanobut.logger.Logger;


/**
 * @author yanggf
 */
public class LogUtil {
    public static boolean isDebug = isApkDebugable();

    public static void v(String tag) {
        if (isDebug)
            Logger.v(tag);
    }

    public static void i(String tag) {
        if (isDebug)
            Logger.i(tag);
    }

    public static void e(String tag) {
        if (isDebug)
            Logger.e(tag);
    }

    public static void w(String tag) {
        if (isDebug)
            Logger.w(tag);
    }

    public static <T extends Result> void d(String tag) {
        if (isDebug)
            Logger.d(tag);
    }


    public static <T extends Result> void i(T t) {
        if (isDebug)
            Logger.i(GsonUtil.getJson(t));
    }

    public static boolean isApkDebugable() {
        try {
            ApplicationInfo info = ModelApp.sContext.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }
}
