package com.chao.bookviki.model.http.api;

import com.chao.bookviki.model.bean.BookListBean;
import com.chao.bookviki.model.bean.RepliesListBean;
import com.chao.bookviki.model.http.response.BookHttpResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jessica on 2017/3/21.
 */

public interface BookApis {
   // String HOST = "http://news-at.zhihu.com/api/4/";
    String CONTENTHOST = "https://www.v2ex.com/";
    String HOST = "http://169.254.242.129:8080/SpringMVC/bookapi/";
    //String HOST = "https://api.leancloud.cn/";

    /**
     * 详情
     */
 //   @GET("news/{id}")
  //  Observable<BookDetailBean> getBookDetailInfo(@Path("id") int id);

   // @GET("story-extra/{id}")
   // Observable<BookDetailExtraBean> getBookDetailExtraInfo(@Path("id") int id);

    /**
     * 日报的短评论
     */
   // @GET("story/{id}/short-comments")
  //  Observable<BookCommentBean> getBookShortCommentInfo(@Path("id") int id);

    /**
     * 日报的长评论
     */
  //  @GET("story/{id}/long-comments")
 //   Observable<BookCommentBean> getBookLongCommentInfo(@Path("id") int id);



    /**
     * 获取主题信息
     * @return
     */
  //  @GET("/api/topics/show.json")
   // Observable<List<NodeListBean>> getTopicInfo(@Query("id") String id);

    /**
     * 获取主题回复
     * @return
     */
    @GET("/BookSpringMVC/bookapi/4/queryId/{queryId}")
    Observable<List<RepliesListBean>> getRepliesList(@Path("queryId") String queryId);


    @GET("/BookSpringMVC/bookapi/3/type/{type}/start/{start}/number/{number}")
    Observable<BookHttpResponse<List<BookListBean>>> getBookList(@Path("type") String type, @Path("start")int start, @Path("number")int number);

    /**
     * 热门推荐
     */
    @GET("1.1/classes/Entry")
    Observable<BookHttpResponse<List<BookListBean>>> getBookHot(@Header("X-LC-Id") String id,
                                                                @Header("X-LC-Sign") String sign,
                                                                @Query("where") String where,
                                                                @Query("order") String order,
                                                                @Query("include") String include,
                                                                @Query("limit") int limit);

 //@POST("ajax.mobileSword")
 //Observable<String> login(@QueryMap HashMap<String,String> paramsMap);

}
