package com.codeest.geeknews.model.http.api;

import com.codeest.geeknews.model.bean.GoldListBean;
import com.codeest.geeknews.model.http.response.GoldHttpResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by codeest on 16/11/27.
 */

public interface GoldApis {

    //String HOST = "https://api.leancloud.cn/";
    String HOST = "http://169.254.242.129:8080/SpringMVC/bookapi/";



   /* @GET("1.1/classes/Entry")
    Observable<GoldHttpResponse<List<GoldListBean>>> getGoldList(@Header("X-LC-Id") String id,
                                                                 @Header("X-LC-Sign") String sign,
                                                                 @Query("where") String where,
                                                                 @Query("order") String order,
                                                                 @Query("include") String include,
                                                                 @Query("limit") int limit,
                                                                 @Query("skip") int skip);*/



   /* @GET("1.1/classes/Entry")
    Observable<GoldHttpResponse<List<GoldListBean>>> getGoldHot(@Header("X-LC-Id") String id,
                                                                @Header("X-LC-Sign") String sign,
                                                            @Query("where") String where,
                                                            @Query("order") String order,
                                                            @Query("include") String include,
                                                            @Query("limit") int limit);*/


    @GET("/BookSpringMVC/bookapi/3/type/{type}/start/{start}/number/{number}")
    Observable<GoldHttpResponse<List<GoldListBean>>> getGoldList(@Path("type") String type, @Path("start")int start, @Path("number")int number);

    /**
     * 热门推荐
     */
    @GET("1.1/classes/Entry")
    Observable<GoldHttpResponse<List<GoldListBean>>> getGoldHot(@Header("X-LC-Id") String id,
                                                                @Header("X-LC-Sign") String sign,
                                                                @Query("where") String where,
                                                                @Query("order") String order,
                                                                @Query("include") String include,
                                                                @Query("limit") int limit);

}
