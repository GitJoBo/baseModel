package com.kinbong.model.eventbean;

/**
 * Created by Administrator on 2017/3/10.
 */

public final class MainPageEvent {
    public final static int RELOGIN=0;
    public int pos;
    public MainPageEvent(int pos){
        this.pos=pos;
    }
}
