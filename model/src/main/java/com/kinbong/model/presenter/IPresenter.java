package com.kinbong.model.presenter;


import com.kinbong.model.iview.BaseView;

public interface IPresenter<V extends BaseView> {

    void attach(V mvpView);

    void detach();
    boolean isViewAttached();
    void checkViewAttached();
}
