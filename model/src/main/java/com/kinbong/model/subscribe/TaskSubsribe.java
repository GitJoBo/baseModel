package com.kinbong.model.subscribe;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/18.
 */
public abstract class TaskSubsribe<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }
}
