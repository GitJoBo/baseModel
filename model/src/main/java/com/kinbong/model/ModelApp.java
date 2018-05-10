package com.kinbong.model;

import android.app.Application;
import android.content.Context;


/**
 * Created by Administrator on 2017/3/10.
 */

public final class ModelApp {

    public static Context sContext;

    public final static void init(Context application){
        sContext = application;
//        Stetho.initializeWithDefaults(sContext);
//
    }
}
