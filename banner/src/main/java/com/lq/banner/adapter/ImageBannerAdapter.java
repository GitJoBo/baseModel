package com.lq.banner.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


/**
 * Created by Administrator on 2017/1/9.
 */

public abstract class ImageBannerAdapter<T> implements BaseBannerAdapter<T>{
    protected ImageView imageView;
    private ArrayList<View> list = new ArrayList<>();

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        list.add(imageView);
        return imageView;
    }

    @Override
    public void destroyView(View view) {
        view.destroyDrawingCache();
    }

    public void destroyViews(){
        if (list!=null &&list.size()>0)
        for (View v:list){
            destroyView(v);
        }
    }
}
