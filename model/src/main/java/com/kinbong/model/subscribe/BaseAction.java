package com.kinbong.model.subscribe;


import rx.functions.Func1;

/**
 * Created by Administrator on 2017/1/19.
 */

public class BaseAction<T ,R> implements Func1<T,R> {
    @Override
    public  R call(T t) {

        return null;
    }
}
