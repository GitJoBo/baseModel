package com.kinbong.model.subscribe;

import com.kinbong.model.iview.ShowErrorView;
import com.kinbong.model.bean.Result;
import com.kinbong.model.util.NetWorkUtil;


/**
 * Created by Administrator on 2016/6/28.
 */
public abstract class BaseErrorSubsribe<T extends Result,V extends ShowErrorView> extends BaseSubsribe<T ,V> {
    public final static int ERROR=0;
    public final static int NET_ERROR=1;
    public final static int EMPTY=2;

    public BaseErrorSubsribe(V view) {
        super(view);
    }

    @Override
    public void onError(Throwable e) {
        if (mView==null) return;
        mView.hideLoading();
        if(NetWorkUtil.isNet()){
            mView.showError(ERROR);
        }else {
            mView.showError(NET_ERROR);
        }
    }

    @Override
    public void onNext(T t) {
        if(t==null){
            if(mView!=null)
            mView.showError(ERROR);
            return;
        }
        super.onNext(t);
    }


}
