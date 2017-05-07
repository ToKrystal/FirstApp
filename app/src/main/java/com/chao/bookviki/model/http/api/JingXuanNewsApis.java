package com.chao.bookviki.model.http.api;

import com.chao.bookviki.model.bean.JingXuanNewsBean;
import com.chao.bookviki.model.http.response.JingXuanNewsResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jessica on 2017/5/7.
 */

public interface JingXuanNewsApis {
    String HOST = "http://wangyi.butterfly.mopaasapp.com/";
    ///api?type=war&page=1&limit=10
    //http://wangyi.butterfly.mopaasapp.com/news/api?type=war&page=1&limit=10
    //http://wangyi.butterfly.mopaasapp.com/api?type=war&page=1&limit=10

    /**
     * 获取精选新闻列表
     * @param type
     * @param page
     * @param limit
     * @return
     */
    @GET("/news/api/")
    Observable<JingXuanNewsResponse<List<JingXuanNewsBean>>> getJingXuanNews(@Query("type") String type, @Query("page") int page, @Query("limit") int limit);
}
