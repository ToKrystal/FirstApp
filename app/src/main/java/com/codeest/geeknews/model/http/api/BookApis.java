package com.codeest.geeknews.model.http.api;

import com.codeest.geeknews.model.bean.BookCommentBean;
import com.codeest.geeknews.model.bean.BookDetailBean;
import com.codeest.geeknews.model.bean.BookDetailExtraBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Jessica on 2017/3/21.
 */

public interface BookApis {
    String HOST = "http://news-at.zhihu.com/api/4/";

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
}
