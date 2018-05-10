package com.kinbong.base;

import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Administrator on 2016/11/2.
 */

public class BaseApp {
    public static Context sContext;

    public static void init(Context context) {
        sContext = context;
        MultiDex.install(context);
    }


}
