package com.lq.banner.adapter;

/**
 * Created by Sai on 15/12/14.
 * @param <T> 任何你指定的对象
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface BaseBannerAdapter<T>{
    View createView(Context context);
    void updateUI(Context context,int position,T data);
    void destroyView(View view);
}