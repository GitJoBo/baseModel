package com.kinbong.model.util;

import android.content.Context;
import android.widget.Toast;

import com.kinbong.model.ModelApp;


/**
 * Created by Administrator on 2016/11/3.
 */

public class ToastUtil {

    /**
     * @param context
     * 上下文环境
     * @param message
     * toast 的信息
     */
    private static Toast mToast = null;

    public static final void showToast(String message) {
        if (null == mToast) {
            mToast = Toast.makeText(ModelApp.sContext, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();

    }


    public static final void showToast(int message) {

        if (null == mToast) {
            mToast = Toast.makeText(ModelApp.sContext, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();

    }




}
