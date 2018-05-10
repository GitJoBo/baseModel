package com.lq.banner.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


import com.lq.banner.R;
import com.lq.banner.view.BannerLoopViewPager;

import java.util.List;


/**
 * Created by Sai on 15/7/29.
 */
public class BannerPageAdapter<T> extends PagerAdapter {
    protected List<T> mDatas;
//    private View.OnClickListener onItemClickListener;
    private boolean isLoop = true;
    private BannerLoopViewPager viewPager;
    private final int MULTIPLE_COUNT = 300;

    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
        int realPosition = position % realCount;
        return realPosition;
    }

    @Override
    public int getCount() {
        return isLoop ? getRealCount()*MULTIPLE_COUNT : getRealCount();
    }

    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = toRealPosition(position);

        View view = getView(realPosition, null, container);
//        if(onItemClickListener != null) view.setOnClickListener(onItemClickListener);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        mAdapter.destroyView(view);
        container.removeView(view);
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setCanLoop(boolean canLoop) {
        this.isLoop = canLoop;
    }

    public void setViewPager(BannerLoopViewPager viewPager) {
        this.viewPager = viewPager;
    }
    BaseBannerAdapter mAdapter = null;
    public BannerPageAdapter(BaseBannerAdapter adapter, List<T> datas) {
        this.mAdapter = adapter;
        this.mDatas = datas;
    }

    public View getView(int position, View view, ViewGroup container) {

        if (view == null) {

            view = mAdapter.createView(container.getContext());
            view.setTag(R.id.cb_item_tag, mAdapter);
        } else {
            mAdapter = (BaseBannerAdapter<T>) view.getTag(R.id.cb_item_tag);
        }
        if (mDatas != null && !mDatas.isEmpty())
            mAdapter.updateUI(container.getContext(), position, mDatas.get(position));
        return view;
    }

}
