package com.kinbong.base.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/12/23.
 */

public class EventBusUtil {
    public final static void register(Object o){
        if(!isRegistered(o))
        EventBus.getDefault().register(o);
    }
    public final static void unregister(Object o){
        if(isRegistered(o))
        EventBus.getDefault().unregister(o);
    }
    public final static boolean isRegistered(Object o){
        return EventBus.getDefault().isRegistered(o);
    }

    public static void post(Object o) {
       EventBus.getDefault().post(o);
    }
}

