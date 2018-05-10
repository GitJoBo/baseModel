package com.kinbong.model.iview;


import java.util.List;

/**
 * Created by Administrator on 2016/5/6 0006.
 */
public interface ListDataView<T>  extends ShowErrorView {
    void refresh(List<T> data,boolean hasmore);
    void loadMore(List<T> data,boolean hasmore);
    void clearLoad();
}
