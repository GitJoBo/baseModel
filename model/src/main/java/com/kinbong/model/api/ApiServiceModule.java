package com.kinbong.model.api;

import android.text.TextUtils;



import com.kinbong.model.util.CacheUtil;
import com.kinbong.model.util.GsonUtil;
import com.kinbong.model.util.LogUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class ApiServiceModule {
//    public static ApiService mApiService=  new Retrofit.Builder().client(getOkHttpClient())
//            .baseUrl(ApiDefine.IP)
//    .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//            .build().create(ApiService.class);


    public static OkHttpClient getOkHttpClient(boolean isHttpCache) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder. connectTimeout(CacheUtil.OUT_TIME, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder. retryOnConnectionFailure(true);
        if(isHttpCache){
            String saveDirPath = CacheUtil.getSaveDirPath();
            if(!TextUtils.isEmpty(saveDirPath))
                builder.cache(new Cache(new File(CacheUtil.getSaveDirPath()),CacheUtil.MAX_SIZE));
            builder.addInterceptor(new HttpCacheIntercepter());
            builder.addNetworkInterceptor(new TimeOutCacheIntercepter());
        }else {
            if(LogUtil.isDebug)
            builder.addInterceptor(new LogIntercepter());
        }
//        if(LogUtil.isDebug)
//        builder.addNetworkInterceptor(new StethoInterceptor());
        return builder.build();
    }


    public final static ApiService getApiService(boolean isHttpCache){
        return getBaseApiService(ApiService.class,isHttpCache);
    }

//    public final static GoodsManagerApiService getGMApiService(boolean isHttpCache){
//        return getBaseApiService(GoodsManagerApiService.class,isHttpCache);
//    }

    public static<T> T getBaseApiService(Class<T> t,boolean isHttpCache) {
        return  new Retrofit.Builder().client(getOkHttpClient(isHttpCache))
                .baseUrl(ApiDefine.IP)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(t);
    }


    public final static ApiService getApiService() {
        return getApiService(false);
    }



}
