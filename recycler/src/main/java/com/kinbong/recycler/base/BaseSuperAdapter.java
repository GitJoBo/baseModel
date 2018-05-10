package com.kinbong.recycler.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Base of SuperAdapter.
 * Created by Cheney on 15/11/28.
 */
public abstract class BaseSuperAdapter<T> extends  RecyclerView.Adapter<BaseViewHolder>  {;

    protected int mLayoutResId;

    protected List<T> mList;
    protected BaseViewHolder.OnClickListen mListen;

    public BaseSuperAdapter(List<T> list, int layoutResId) {
        this.mList = list ;
        this.mLayoutResId = layoutResId;
    }
    public BaseSuperAdapter(int layoutResId) {
        this(new ArrayList<T>(),layoutResId);
    }

    public List<T> getList() {
        return mList;
    }



    @Override
    public int getItemCount() {
        if(mList==null)return 0;
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutResId, parent, false);
        return new BaseViewHolder(view);
    }
    public void add(T item) {
        mList.add(item);
        int index = mList.size() - 1;

        notifyItemInserted(index);
    }

    public void insert(int index, T item) {
        mList.add(index, item);
        notifyItemInserted(index);
    }

    public void addAllAotify(List<T> items) {
        if (items == null || items.size() == 0) {
            return;
        }
        int start = getItemCount();
        mList.addAll(items);
        notifyItemRangeInserted(start, items.size());
//        notifyDataSetChanged();
    }
    public void addAll(List<T> items){
        if (items == null || items.size() == 0) {
            return;
        }
        mList.addAll(items);
    }
    public void notifyEnd(){
        if(mList.size()>0)return;
        int start = mList.size();
        notifyItemChanged(getItemCount());
    }
    public void refresh(List<T> items){
        mList.clear();
        if (items == null || items.size() == 0) {
            myNotifyDataSetChanged();
            return;
        }
        mList.addAll(items);
        myNotifyDataSetChanged();
    }

    public void myNotifyDataSetChanged() {
        notifyDataSetChanged();
    }


    public void remove(T item) {
        if (mList.contains(item)) {
            int index = mList.indexOf(item);
            remove(index);
        }
    }

    public void remove(int index) {
        if(mList.size()<1){
            myNotifyDataSetChanged();
        }else {
            mList.remove(index);
            notifyItemRemoved(index);
            notifyItemChanged(index);
        }
    }



    public void set(T oldItem, T newItem) {
        set(mList.indexOf(oldItem), newItem);
    }

    public void set(int index, T item) {
        mList.set(index, item);

        notifyItemChanged(index);
    }

    public void replaceAll(List<T> items) {
        clear();
        addAll(items);
    }

    public boolean contains(T item) {
        return mList.contains(item);
    }

    public void clear() {
        mList.clear();
        myNotifyDataSetChanged();
    }
    public void setOnclickListen(BaseViewHolder.OnClickListen listen){

        mListen = listen;
    }

    public T getItemData(int position){
        return mList.get(position);
    }

    public List<T> getmList(){
        return  mList;
    }
;
}
