package com.kinbong.base.ui.activity;

import com.kinbong.model.iview.BaseView;
import com.kinbong.model.presenter.BasePresenter;


/**
 * Created by Administrator on 2016/9/12.
 */
public abstract class BaseLoadActivity<P extends BasePresenter> extends BaseActivity implements BaseView{

    protected P mPresenter;

    @Override
    protected void initPresenter() {
        mPresenter = createPresenter();
        if(mPresenter!=null)
            mPresenter.attach(this);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detach();
            mPresenter=null;
        }
    }

    @Override
    public void showErrorInfo(String msg) {
        showTip(msg);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mPresenter!=null)mPresenter.getCompositeSub().clear();
    }
}
