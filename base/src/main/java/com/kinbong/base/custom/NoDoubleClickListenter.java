package com.kinbong.base.custom;


import android.view.View;

import com.kinbong.model.util.ToastUtil;

import java.util.Calendar;

/**
 * 避免连续点击造成多次事件
 * Created by Administrator on 2017/8/21.
 */

public abstract class NoDoubleClickListenter implements View.OnClickListener {

    public final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }else {
            ToastUtil.showToast("请不要连续点击");
        }
    }
    public abstract void onNoDoubleClick(View v);
}