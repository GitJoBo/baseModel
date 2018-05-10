package com.kinbong.model.presenter;

import com.kinbong.model.iview.BaseView;
import com.kinbong.model.iview.ShowLoadView;
import com.kinbong.model.api.ApiService;
import com.kinbong.model.api.ApiServiceModule;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V extends BaseView> implements IPresenter<V> {
    private WeakReference<V> mReference;
    private CompositeSubscription mCs;

    @Override
    public void attach(V mvpView) {
        mReference = new WeakReference<>(mvpView);
    }

    @Override
    public void detach() {
        unSubscribe();
        if(mReference!=null){
            mReference.clear();
            mReference=null;
        }

    }

    public void unSubscribe() {
        if(mCs!=null){
            if(!mCs.isUnsubscribed())mCs.unsubscribe();
            mCs=null;
        }
    }

    public    CompositeSubscription getCompositeSub() {
        if(mCs==null||mCs.isUnsubscribed())
            mCs =  new CompositeSubscription();
        return mCs;
    }

    protected V getView(){
        if(mReference!=null)
            return mReference.get();
        return null;
    }

    public void add(Observable o, Subscriber subscriber){
        getCompositeSub().add(o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber));
    }
    public void addSubscription(Subscription subscriber){
        CompositeSubscription compositeSub = getCompositeSub();
        compositeSub.add(subscriber);
    }
    public void add(Observable o, Action1 subscriber){
        getCompositeSub().add(o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber));
    }

    public void add(Observable o, Subscriber s, boolean isLoadding) {
        V view = getView();
        if(isLoadding&&view!=null&&view instanceof ShowLoadView){
            ((ShowLoadView) view).showLoading();
        }
        add(o, s);
    }

    protected ApiService getApiService(){
        return getApiService(ApiService.class);
    }
    protected ApiService getCacheApiService(){
        return getCacheApiService(ApiService.class);
    }

    protected <T>T getApiService(Class<T> clazz){
        return ApiServiceModule.getBaseApiService(clazz,false);
    }
    protected <T>T getCacheApiService(Class<T> clazz){
        return ApiServiceModule.getBaseApiService(clazz,true);
    }

    public boolean isViewAttached() {
        return getView() != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call IPresenter.attachView(BaseMvpView) before" +
                    " requesting data to the IPresenter");
        }
    }



}
