package com.lq.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.lq.banner.view.BannerLoopViewPager;
import com.lq.banner.view.PhotoScaleBannerView;

/**
 * Created by Administrator on 2016/10/8.
 */

public class PhotoScaleBanner<T> extends Banner<T> {

    public PhotoScaleBanner(Context context) {
        super(context);
    }

    public PhotoScaleBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoScaleBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PhotoScaleBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @NonNull
    @Override
    protected BannerLoopViewPager getBannerLoopViewPager(Context context) {
        return new PhotoScaleBannerView(context);
    }
}
