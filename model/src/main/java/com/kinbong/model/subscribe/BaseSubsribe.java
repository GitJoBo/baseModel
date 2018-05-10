package com.kinbong.model.subscribe;


import android.text.TextUtils;

import com.kinbong.model.R;
import com.kinbong.model.bean.Result;
import com.kinbong.model.iview.ShowLoadView;
import com.kinbong.model.util.DevicesUtil;
import com.kinbong.model.util.HandlerUtil;
import com.kinbong.model.util.LogUtil;
import com.kinbong.model.util.MySP;
import com.kinbong.model.util.NetWorkUtil;
import com.kinbong.model.util.ToastUtil;

import rx.Subscriber;

/**
 * 观察者
 */
public abstract class BaseSubsribe<T extends Result,V extends ShowLoadView> extends Subscriber<T>  {

    protected V mView;

    public  BaseSubsribe( V view){
        mView = view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d( "onStart");
    }

    @Override
    public void onNext(T t) {
//        Log.i(TAG, "response" + t.toString());
        if(t==null){
            //ToastUtil.showToast(R.string.data_load_error);
            return;
        }
//        LogUtil.i(t);
        if(mView==null){
            LogUtil.d("mView = null");
            return;
        }
        int code = t.code;
        String errorInfo = t.msg;

        if(code== MySP.LOAD_SUCCESS_CODE){
            onSuccess(t);
//            try {
//                onSuccess(t);
//            }catch (Exception e){
//                LogUtil.d("数据模型不匹配，e="+e);
//                ToastUtil.showToast("暂无数据！");
//            }

        }else {
            onError(t);
            if(code== MySP.TOKEN_EMPTY){
                clearLogin();
            }else {
                mView.showErrorInfo(errorInfo);
//                ToastUtil.showToast(errorInfo);
            }
        }
    }

    private void clearLogin() {
        HandlerUtil.postDelayed(()->  mView.reLogin(),1000);
    }

    protected void onError(T t) {}

    @Override
    public void onCompleted() {
        LogUtil.d( "onCompleted");
        if(mView==null){
            return;
        }
        mView.hideLoading();
    }

    public abstract void onSuccess(T result);

    @Override
    public void onError(Throwable e) {
        LogUtil.i( "BaseSubsribe，onError==" + e.getMessage());
        ToastUtil.showToast("网络链接异常");
        if(!DevicesUtil.chcekThread())return;
        NetWorkUtil.isNetAndShow();
        if (mView==null){
            return;
        }
        mView.hideLoading();
        //ToastUtil.showToast("请求失败,转换异常");
    }




}
