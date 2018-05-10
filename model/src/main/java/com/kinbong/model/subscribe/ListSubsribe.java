package com.kinbong.model.subscribe;


import com.kinbong.model.iview.ListDataView;
import com.kinbong.model.bean.Result;
import com.kinbong.model.util.LogUtil;


/**
 * Created by Administrator on 2016/5/11 0011.
 */
public abstract class ListSubsribe<T extends Result,V extends ListDataView> extends BaseErrorSubsribe<T,V> {
    public ListSubsribe(V view) {
        super(view);
    }
    @Override
    public void onError(Throwable e) {
        LogUtil.d("e="+e);
        if(mView!=null)
            mView.clearLoad();
        super.onError(e);
    }

}
