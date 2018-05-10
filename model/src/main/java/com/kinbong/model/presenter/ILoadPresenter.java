package com.kinbong.model.presenter;


import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public interface ILoadPresenter {
    void addObservable(Observable o, Subscriber s, boolean isLoadding);
//    ApiService getApiService();
}
