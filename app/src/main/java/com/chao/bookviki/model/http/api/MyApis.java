package com.chao.bookviki.model.http.api;

import com.chao.bookviki.model.bean.VersionBean;
import com.chao.bookviki.model.http.response.MyHttpResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by codeest on 16/10/10.
 * https://github.com/codeestX/my-restful-api
 */

public interface MyApis {

    String HOST = "http://codeest.me/api/bookviki/";

    String APK_DOWNLOAD_URL = "http://codeest.me/apk/bookviki.apk";

    /**
     * 获取最新版本信息
     * @return
     */
    @GET("version")
    Observable<MyHttpResponse<VersionBean>> getVersionInfo();

}