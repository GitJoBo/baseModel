package com.kinbong.base.ui.fragment;

import com.kinbong.model.iview.ShowLoadView;
import com.kinbong.model.presenter.BasePresenter;

/**
 * Created by Administrator on 2016/5/4 0004.
 */
public abstract  class BaseLoadFragment<P extends BasePresenter> extends BaseFragment implements ShowLoadView {
    protected P mPresenter;

    @Override
    protected void initLoad() {
        super.initLoad();
        if (mPresenter == null)
        mPresenter=createPresenter();
        if(mPresenter!=null)mPresenter.attach(this);
    }

    protected abstract P createPresenter() ;

    @Override
    public void onDestroyView() {

        super.onDestroyView();
//        if(mContentView!=null)
//        ((ViewGroup) mContentView.getParent()).removeView(mContentView);
        if(mPresenter!=null){
            mPresenter.detach();
            mPresenter=null;
        }
    }

    @Override
    public void showLoading() {
        mActivity.showLoading(mContentView);
    }

    @Override
    public void hideLoading() {
        mActivity.hideLoading();
    }

    @Override
    public void reLogin() {
        mActivity.reLogin();
    }

}
