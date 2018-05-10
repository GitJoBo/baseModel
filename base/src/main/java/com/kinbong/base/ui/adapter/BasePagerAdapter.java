package com.kinbong.base.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Administrator on 2017/1/19.
 */

public abstract class BasePagerAdapter extends FragmentStatePagerAdapter {
    protected String[] mTitles;

    public BasePagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles = titles;
    }


    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
