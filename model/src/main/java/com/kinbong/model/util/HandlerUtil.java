package com.kinbong.model.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2016/3/22 0022.
 */
public class HandlerUtil {


    private static Handler mHandler;


    public static final void post(Runnable runnable){
        getHandler().post(runnable);
    }

    public static final void postDelayed(Runnable runnable,int delayed){
        getHandler().postDelayed(runnable, delayed);
    }
     public static final void removeAllCallbacks(Object token){
         getHandler().removeCallbacksAndMessages(token);
    }
    public  static final void remove(Runnable runnable){
        getHandler().removeCallbacks(runnable);
    }

    private static Handler getHandler() {
        if(mHandler==null){
            synchronized (HandlerUtil.class){
                if(mHandler==null){
                    mHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return mHandler;
    }
}
