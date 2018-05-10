package com.kinbong.base.ui.fragment;

/**
 * 宿主FragmentActivity需要实现BackHandledIntegerface，子Fragment会通过该接口告诉宿主FragmentActivity自己是当前屏幕可见的Fragment。
 * Created by Administrator on 2017/9/25.
 */

public interface BackHandledInterface {
    void setSelectedFragment(BackHandledFragment selectedFragment);
}
