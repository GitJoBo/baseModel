package com.kinbong.base.utils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/6/14.
 */
public class RxManager {
    private static RxManager mRxhHelper;
    private CompositeSubscription mSubscriptions;

    private RxManager(){

    }
    public static RxManager instance(){
        if(mRxhHelper==null){
            synchronized (RxManager.class){
                if(mRxhHelper==null)
                    mRxhHelper=new RxManager();

            }
        }
        return mRxhHelper;
    }
    public void addObservable(Observable o, Subscriber subscriber){
        getCompositeSubscription().add(o.compose(rxSchedulerHelper()).subscribe(subscriber));
    }
    public void addObservable(Observable o, Action1 subscriber){
        getCompositeSubscription().add(o.compose(rxSchedulerHelper()).subscribe(subscriber));
    }
    public CompositeSubscription getCompositeSubscription(){
        if (mSubscriptions == null || mSubscriptions.isUnsubscribed()) {
            mSubscriptions= new CompositeSubscription();
        }
        return mSubscriptions;
    }
    public void unsubscribe(){
        if(mSubscriptions!=null){
            mSubscriptions.unsubscribe();
            mSubscriptions=null;
        }
    }
    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return  observable->{
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

        };
    }
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper2() {    //compose简化线程
        return observable-> {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());

        };
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(subscriber->{
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }

        });
    }


}
