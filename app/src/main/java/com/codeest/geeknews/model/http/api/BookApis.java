package com.codeest.geeknews.model.http.api;

import com.codeest.geeknews.model.bean.BookCommentBean;
import com.codeest.geeknews.model.bean.BookDetailBean;
import com.codeest.geeknews.model.bean.BookDetailExtraBean;
import com.codeest.geeknews.model.bean.NodeListBean;
import com.codeest.geeknews.model.bean.RepliesListBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jessica on 2017/3/21.
 */

public interface BookApis {
    String HOST = "http://news-at.zhihu.com/api/4/";
    String CONTENTHOST = "https://www.v2ex.com/";

    /**
     * 详情
     */
    @GET("news/{id}")
    Observable<BookDetailBean> getBookDetailInfo(@Path("id") int id);

    @GET("story-extra/{id}")
    Observable<BookDetailExtraBean> getBookDetailExtraInfo(@Path("id") int id);

    /**
     * 日报的短评论
     */
    @GET("story/{id}/short-comments")
    Observable<BookCommentBean> getBookShortCommentInfo(@Path("id") int id);

    /**
     * 日报的长评论
     */
    @GET("story/{id}/long-comments")
    Observable<BookCommentBean> getBookLongCommentInfo(@Path("id") int id);



    /**
     * 获取主题信息
     * @return
     */
    @GET("/api/topics/show.json")
    Observable<List<NodeListBean>> getTopicInfo(@Query("id") String id);

    /**
     * 获取主题回复
     * @return
     */
    @GET("/api/replies/show.json")
    Observable<List<RepliesListBean>> getRepliesList(@Query("topic_id") String id);
}
