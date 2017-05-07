package com.chao.bookviki.model.http.api;

import com.chao.bookviki.model.bean.YingWenYuLuBean;
import com.chao.bookviki.model.http.response.YingWenYuLuResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jessica on 2017/5/7.
 */

public interface YingWenYuLuApis {
    String HOST = "http://route.showapi.com/";
    //412cbe4f62db4cd09c27c7c13b4a8fc1
    //37526

    @GET("/1211-1/")
    Observable<YingWenYuLuResponse<List<YingWenYuLuBean>>> getYingWenYuLus(@Query("count") int count, @Query("showapi_appid") int showapi_appid, @Query("showapi_sign") String showapi_sign);
}
