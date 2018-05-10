package com.kinbong.base.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import com.kinbong.base.ui.activity.BaseActivity;
import com.kinbong.base.ui.activity.BaseLoadActivity;
import com.kinbong.model.util.LogUtil;
import com.kinbong.base.utils.UIUtils;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public abstract class BaseFragment extends Fragment {
    //BackHandledFragment   BackHandledInterface
    protected BaseActivity mActivity;

    protected boolean isFristVisible = true;
    private Unbinder mBind;
    private boolean isFristData = true;
    protected View mContentView;
    private boolean isFirstInvisible;
    protected boolean isVisibleToUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LogUtil.d("oncreat");
//        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mActivity = (BaseActivity) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 避免多次从xml中加载布局文件
        if (mContentView == null) {
//            LogUtil.d("onCreateView");
            if (getContentViewLayoutID() != 0) {
                mContentView = inflater.inflate(getContentViewLayoutID(), null);
                mBind = ButterKnife.bind(this, mContentView);
            }

        } else {
            ViewParent parent = mContentView.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(mContentView);
            }
        }
        mActivity = (BaseActivity) getActivity();
        return mContentView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            if (isFristVisible) {
                isFristVisible = false;
                userFristVisible();
            }
            userVisible();
        } else {
            userInvisible();
        }
    }

    protected void userInvisible() {
//        LogUtil.d("userInvisible");
    }


    protected void userVisible() {
//        LogUtil.d("userVisible");
    }

    protected void userFristVisible() {
//        LogUtil.d("userFristVisible");
    }


    protected View findViewById(int viewId) {
        return mContentView.findViewById(viewId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        LogUtil.d("onActivityCreated");
        if (isFristData) {
            isFristData = false;
            initLoad();
            init();
        }
    }

    protected void initLoad() {

    }

    protected FragmentManager getSupportFragmentManager() {
        if (mActivity == null)
            mActivity = (BaseActivity) getActivity();
        return mActivity.getSupportFragmentManager();
    }

    protected View getContentView() {
        return mContentView;
    }

    @Override
    public void onDestroyView() {
        if (mBind != null) {
            mBind.unbind();
            mBind = null;
        }
        if (mActivity != null) {
            mActivity = null;
        }
        super.onDestroyView();
    }

    protected abstract void init();

    public void showErrorInfo(String msg) {
        UIUtils.showToast(msg);
    }


    protected abstract int getContentViewLayoutID();

}
