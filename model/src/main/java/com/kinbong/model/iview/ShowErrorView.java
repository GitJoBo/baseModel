package com.kinbong.model.iview;

/**
 * Created by Administrator on 2016/11/3.
 */

public interface ShowErrorView extends ShowLoadView{
    void showError(int type);
    void showEmpty();
    void initLoadData();
    void hideErrorLayout();
}
