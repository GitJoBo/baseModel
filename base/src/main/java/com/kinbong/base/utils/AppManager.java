package com.kinbong.base.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class AppManager {
	
	private static Stack<Activity> mActivityStack;
	private static AppManager instance;
	
	private AppManager(){}
	/**
	 * 单一实例
	 */
	public static AppManager getAppManager(){
		if(instance==null){
			instance=new AppManager();
		}
		return instance;
	}
	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity){
		if(mActivityStack ==null){
			mActivityStack =new Stack<Activity>();
		}
		mActivityStack.add(activity);
	}
	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity(){
		Activity activity= mActivityStack.lastElement();
		return activity;
	}
	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity(){
		Activity activity= mActivityStack.lastElement();
		finishActivity(activity);
	}
	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity){
		if(activity!=null){
			mActivityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}
	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls){
		Stack<Activity> activityStack = AppManager.mActivityStack;

		for (int i=0;i<activityStack.size();i++) {
			Activity activity = activityStack.get(i);
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}


	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity(){
		for (int i = 0, size = mActivityStack.size(); i < size; i++){
            if (null != mActivityStack.get(i)){
            	mActivityStack.get(i).finish();
			}
	    }
		mActivityStack.clear();
	}
	/**
	 * 退出应用程序
	 */
	public void appExit() {
		try {
			finishAllActivity();
//			ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//			activityMgr.re(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {	}
	}

	public void finishOtherActivity(Activity activity) {
		Stack<Activity> activityStack = mActivityStack;
		if(activityStack.size()<=1)return;
		for (int i = 0, size = activityStack.size(); i < size; i++){
			Activity a = activityStack.get(i);
			if (a!=null&&a!=activity){
				a.finish();
				mActivityStack.remove(i);
			}
		}
	}
	public void finishOtherActivity(Class clazz) {
		Stack<Activity> activityStack = mActivityStack;
		if(activityStack.size()<=1)return;
		for (int i = 0, size = activityStack.size(); i < size; i++){
			Activity a = activityStack.get(i);
			if (a!=null&&a.getClass()!=clazz){
				a.finish();
				mActivityStack.remove(i);
			}
		}
	}

	public boolean  exists(Class<?> clazz){
		Stack<Activity> activityStack = mActivityStack;
		for (int i = 0, size = activityStack.size(); i < size; i++){
			Activity a = activityStack.get(i);
			if (a!=null&&a.getClass().equals( clazz)){
				return true;
			}
		}
		return false;
	}

}