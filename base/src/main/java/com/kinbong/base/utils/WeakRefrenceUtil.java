package com.kinbong.base.utils;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * 引用类型转换
 */
public class WeakRefrenceUtil {
    public static FragmentManager getFragmentManager(FragmentManager manager){
        WeakReference<FragmentManager> f = new WeakReference<>(manager);
        if(f==null){
            return null;
        }
        return f.get();
    }
    public static Context getContext(Context context){
        WeakReference<Context> f = new WeakReference<>(context);
        if(f==null){
            return null;
        }

        return f.get();
    }
    public static Activity getContext(Activity act){
        WeakReference<Activity> w = new WeakReference<>(act);
        if(w==null){
            return null;
        }

        return  w.get();
    }

}
