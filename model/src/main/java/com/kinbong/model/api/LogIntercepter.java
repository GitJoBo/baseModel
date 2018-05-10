package com.kinbong.model.api;


import com.kinbong.model.util.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class LogIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        LogUtil.i(request.toString());
        LogUtil.i(request.url().toString());
        Response originalResponse = chain.proceed(request);
//        if(TextUtils.equals(request.url().encodedPath(),ApiDefine.ACCOUNT_LOGIN))
//        List<String> cookieList =  originalResponse.headers("Set-Cookie");
//        LogUtil.e(cookieList.toString());
//        if(cookieList != null) {
//            for(String s:cookieList) {//Cookie的格式为:cookieName=cookieValue;path=xxx
//                //保存你需要的cookie数据
//                LogUtil.e(s);
//            }
//        }
        return originalResponse;
    }
}
