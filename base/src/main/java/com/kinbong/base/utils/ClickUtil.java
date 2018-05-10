package com.kinbong.base.utils;

/**
 * Created by Administrator on 2016/11/16.
 */

public class ClickUtil {
    private static long lastClickTime;

    public synchronized static final boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
