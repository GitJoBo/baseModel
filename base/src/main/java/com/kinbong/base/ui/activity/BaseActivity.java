package com.kinbong.base.ui.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.kinbong.base.R;
import com.kinbong.base.utils.AppManager;
import com.kinbong.base.utils.EventBusUtil;
import com.kinbong.base.utils.UIUtils;
import com.kinbong.model.eventbean.MainPageEvent;
import com.kinbong.model.util.DevicesUtil;
import com.kinbong.model.util.LogUtil;
import com.kinbong.model.util.MySP;
import com.kinbong.model.util.StringUtil;
import com.kinbong.model.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/1/24.
 */

public abstract class BaseActivity extends AppCompatActivity{
    private View mContentView;
    private Unbinder mBind;
//    private WaitDialog mWaitDialog;
//    private LoadingPopupWindow mLoadingPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//锁定屏幕方向
//        StatusBarUtil.setTransparent2(this);
        super.onCreate(savedInstanceState);
        LogUtil.d("onCreate");
//        锁定屏幕方向
        AppManager.getAppManager().addActivity(this);
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            mBind = ButterKnife.bind(this);
//            StatusBarUtil.setColor(this, MyViewUtil.getColor(R.color.theme));
            initPresenter();
        }
        init();
        initWindow();
    }

    private void initWindow() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    //设置字体大小不随系统变化
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
    protected abstract void init();
    protected void initPresenter() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d("onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("onStop");
    }
    protected void showTip(String str){
//        UIUtils.showSnackbar(str,getContentView());//第一版
//        DialogUtil.getCustomTipDialog2(this,str).show();//第二版
        if (!TextUtils.isEmpty(str)){
            ToastUtil.showToast(str);
        }else {
            ToastUtil.showToast(R.string.data_load_error);
        }

    }

    protected View getContentView() {
        if(mContentView==null)
            mContentView = findViewById(android.R.id.content);
        return mContentView;
    }

    public void onResume() {
        super.onResume();
        LogUtil.d("onResume");
    }
    public void onPause() {
        super.onPause();
        LogUtil.d("onPause");
    }

//    public void setTextColor(){
//        findViewById(R.id.title_iv)
//    }

    protected abstract int getContentViewLayoutID();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("onDestroy");
        // 使用同步，防止出现还未创建成功，就移除的情况
        if(mBind!=null){
            mBind.unbind();
            mBind=null;
        }
        mContentView=null;

//        if(mWaitDialog!=null&&mWaitDialog.isShowing()){
//            mWaitDialog.dismiss();
//            mWaitDialog=null;
//        }
//        if(mLoadingPopupWindow !=null){
//            mLoadingPopupWindow.destory();
//            mLoadingPopupWindow =null;
//        }
        DevicesUtil.fixInputMethodManagerLeak(this);
        AppManager.getAppManager().finishActivity(this);
    }

    public void showLoading() {
        showLoading(getContentView());
    }

    public void showLoading(View view){
//        if(mLoadingPopupWindow ==null)
//            mLoadingPopupWindow =new LoadingPopupWindow(this);
//        DialogUtil.showProgressDialog(this,mLoadingPopupWindow,view);
    }

    public void hideLoading() {
//        if(mLoadingPopupWindow !=null) mLoadingPopupWindow.hide();

    }

    protected void showProgressDialog() {
//        if(mWaitDialog==null){
//            mWaitDialog = new WaitDialog(this);
//            mWaitDialog.setCancelable(false);
//        }
//        if(mWaitDialog!=null&&!mWaitDialog.isShowing()){
//            mWaitDialog.show();
//        }
    }

    protected void hideProgressDialog() {
//        if(mWaitDialog!=null&&mWaitDialog.isShowing()){
//            mWaitDialog.dismiss();
//        }
    }

    public void reLogin(){
        MySP.clearToken();
        MySP.clearLoginInfo();
    }
}
