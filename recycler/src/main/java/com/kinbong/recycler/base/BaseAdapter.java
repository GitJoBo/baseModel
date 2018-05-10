package com.kinbong.recycler.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
public abstract class  BaseAdapter<VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {


    @Override
    public VH onCreateViewHolder(ViewGroup parent, final int viewType) {
        final VH holder = onCreate(parent, viewType);
        return holder;
    }

    protected abstract VH onCreate(ViewGroup parent, int viewType);
}
