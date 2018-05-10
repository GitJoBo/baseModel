package com.kinbong.model.api;

import com.kinbong.model.bean.Result;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by JoBo on 2018/3/24.
 */

public interface TestService {
//    @GET("weatherApi?city=成都")
    @GET(ApiDefine.test)
    Observable<Result> test(@Query("city") String city);
}
