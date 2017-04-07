package com.chao.bookviki.model.http;

import com.chao.bookviki.model.bean.BookListBean;
import com.chao.bookviki.model.bean.CommentBean;
import com.chao.bookviki.model.bean.CreateAccountBean;
import com.chao.bookviki.model.bean.DailyBeforeListBean;
import com.chao.bookviki.model.bean.DailyListBean;
import com.chao.bookviki.model.bean.DetailExtraBean;
import com.chao.bookviki.model.bean.FollowBean;
import com.chao.bookviki.model.bean.GankItemBean;
import com.chao.bookviki.model.bean.GankSearchItemBean;
import com.chao.bookviki.model.bean.HotListBean;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.model.bean.NodeBean;
import com.chao.bookviki.model.bean.NodeListBean;
import com.chao.bookviki.model.bean.RepliesListBean;
import com.chao.bookviki.model.bean.SectionChildListBean;
import com.chao.bookviki.model.bean.SectionListBean;
import com.chao.bookviki.model.bean.ThemeChildListBean;
import com.chao.bookviki.model.bean.ThemeListBean;
import com.chao.bookviki.model.bean.VersionBean;
import com.chao.bookviki.model.bean.WelcomeBean;
import com.chao.bookviki.model.bean.ZhihuDetailBean;
import com.chao.bookviki.model.http.api.BookApis;
import com.chao.bookviki.model.http.api.GankApis;
import com.chao.bookviki.model.http.api.GoldApis;
import com.chao.bookviki.model.http.api.MyApis;
import com.chao.bookviki.model.http.api.VtexApis;
import com.chao.bookviki.model.http.api.ZhihuApis;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.model.http.response.GankHttpResponse;
import com.chao.bookviki.model.http.response.MyHttpResponse;

import java.util.List;

import rx.Observable;

/**
 *
 */
public class RetrofitHelper {

    private ZhihuApis mZhihuApiService;
    private BookApis mBookApiService;
    private GankApis mGankApiService;
    private MyApis mMyApiService;
    private GoldApis mGoldApiService;
    private VtexApis mVtexApiService;

    public RetrofitHelper(ZhihuApis zhihuApiService, GankApis gankApiService,
                          MyApis myApiService, GoldApis goldApiService, VtexApis vtexApiService,BookApis bookApis) {
        this.mZhihuApiService = zhihuApiService;
        this.mGankApiService = gankApiService;
        this.mMyApiService = myApiService;
        this.mGoldApiService = goldApiService;
        this.mVtexApiService = vtexApiService;
        this.mBookApiService = bookApis;
    }

    public Observable<DailyListBean> fetchDailyListInfo() {
        return mZhihuApiService.getDailyList();
    }

    public Observable<DailyBeforeListBean> fetchDailyBeforeListInfo(String date) {
        return mZhihuApiService.getDailyBeforeList(date);
    }

    public Observable<ThemeListBean> fetchDailyThemeListInfo() {
        return mZhihuApiService.getThemeList();
    }

    public Observable<ThemeChildListBean> fetchThemeChildListInfo(int id) {
        return mZhihuApiService.getThemeChildList(id);
    }

    public Observable<SectionListBean> fetchSectionListInfo() {
        return mZhihuApiService.getSectionList();
    }

    public Observable<SectionChildListBean> fetchSectionChildListInfo(int id) {
        return mZhihuApiService.getSectionChildList(id);
    }

    public Observable<ZhihuDetailBean> fetchDetailInfo(int id) {
        return mZhihuApiService.getDetailInfo(id);
    }
   /* public Observable<BookDetailBean> fetchBookDetailInfo(int id) {
        return mBookApiService.getBookDetailInfo(id);
    }*/

    public Observable<DetailExtraBean> fetchDetailExtraInfo(int id) {
        return mZhihuApiService.getDetailExtraInfo(id);
    }
   /* public Observable<BookDetailExtraBean> fetchBookDetailExtraInfo(int id) {
        return mBookApiService.getBookDetailExtraInfo(id);
    }*/

    public Observable<WelcomeBean> fetchWelcomeInfo(String res) {
        return mZhihuApiService.getWelcomeInfo(res);
    }

    public Observable<CommentBean> fetchLongCommentInfo(int id) {
        return mZhihuApiService.getLongCommentInfo(id);
    }

    /*public Observable<BookCommentBean> fetchBookLongCommentInfo(int id) {
        return mBookApiService.getBookLongCommentInfo(id);
    }*/

    public Observable<CommentBean> fetchShortCommentInfo(int id) {
        return mZhihuApiService.getShortCommentInfo(id);
    }
   /* public Observable<BookCommentBean> fetchBookShortCommentInfo(int id) {
        return mBookApiService.getBookShortCommentInfo(id);
    }*/

    public Observable<HotListBean> fetchHotListInfo() {
        return mZhihuApiService.getHotList();
    }

    public Observable<GankHttpResponse<List<GankItemBean>>> fetchTechList(String tech, int num, int page) {
        return mGankApiService.getTechList(tech, num, page);
    }

    public Observable<GankHttpResponse<List<GankItemBean>>> fetchGirlList(int num, int page) {
        return mGankApiService.getGirlList(num, page);
    }

    public Observable<GankHttpResponse<List<GankItemBean>>> fetchRandomGirl(int num) {
        return mGankApiService.getRandomGirl(num);
    }

    public Observable<GankHttpResponse<List<GankSearchItemBean>>> fetchGankSearchList(String query,String type,int num,int page) {
        return mGankApiService.getSearchList(query,type,num,page);
    }



    public Observable<MyHttpResponse<VersionBean>> fetchVersionInfo() {
        return mMyApiService.getVersionInfo();
    }

    /*public Observable<GoldHttpResponse<List<GoldListBean>>> fetchGoldList(String type, int num, int page) {
        return mGoldApiService.getGoldList(Constants.LEANCLOUD_ID,
                Constants.LEANCLOUD_SIGN,
                "{\"category\":\"" + type + "\"}",
                "-createdAt",
                "user,user.installation",
                num,
                page * num);
    }*/
    /*public Observable<GoldHttpResponse<List<GoldListBean>>> fetchGoldList(String type, int start, int number) {
        return mGoldApiService.getGoldList(type,start,number);
       *//* return mGoldApiService.getGoldList(Constants.LEANCLOUD_ID,
                Constants.LEANCLOUD_SIGN,
                "{\"category\":\"" + type + "\"}",
                "-createdAt",
                "user,user.installation",
                number,
                start * number);*//*
    }*/
    public Observable<BookHttpResponse<List<BookListBean>>> fetchBookList(String type, int start, int number) {
        return mBookApiService.getBookList(type,start,number);
       /* return mGoldApiService.getGoldList(Constants.LEANCLOUD_ID,
                Constants.LEANCLOUD_SIGN,
                "{\"category\":\"" + type + "\"}",
                "-createdAt",
                "user,user.installation",
                number,
                start * number);*/
    }

    /*public Observable<GoldHttpResponse<List<GoldListBean>>> fetchGoldHotList(String type, String dataTime, int limit) {
        return mGoldApiService.getGoldHot(Constants.LEANCLOUD_ID, Constants.LEANCLOUD_SIGN,
                "{\"category\":\"" + type + "\",\"createdAt\":{\"$gt\":{\"__type\":\"Date\",\"iso\":\"" + dataTime + "T00:00:00.000Z\"}},\"objectId\":{\"$nin\":[\"58362f160ce463005890753e\",\"583659fcc59e0d005775cc8c\",\"5836b7358ac2470065d3df62\"]}}",
                "-hotIndex", "user,user.installation", limit);
    }*/
    /*public Observable<GoldHttpResponse<List<GoldListBean>>> fetchGoldHotList(String type, String dataTime, int limit) {
       return mGoldApiService.getGoldList("android",0,3);
        *//*return mGoldApiService.getGoldList(Constants.LEANCLOUD_ID,
                Constants.LEANCLOUD_SIGN,
                "{\"category\":\"" + type + "\"}",
                "-createdAt",
                "user,user.installation",
                3,
                3 * 4);*//*
    }*/

    public Observable<BookHttpResponse<List<BookListBean>>> fetchBookHotList(String type, String dataTime, int limit) {
        return mBookApiService.getBookList("android",0,3);
        /*return mGoldApiService.getGoldList(Constants.LEANCLOUD_ID,
                Constants.LEANCLOUD_SIGN,
                "{\"category\":\"" + type + "\"}",
                "-createdAt",
                "user,user.installation",
                3,
                3 * 4);*/
    }

    public Observable<NodeBean> fetchNodeInfo(String name) {
        return mVtexApiService.getNodeInfo(name);
    }

    public Observable<List<NodeListBean>> fetchTopicList(String name) {
        return mVtexApiService.getTopicList(name);
    }

    public Observable<List<NodeListBean>> fetchTopicInfo(String id) {
        return mVtexApiService.getTopicInfo(id);
    }

   /* public Observable<List<RepliesListBean>> fetchRepliesList(String id){
        return mVtexApiService.getRepliesList(id);
    }*/
    public Observable<List<RepliesListBean>> fetchRepliesList(String id){
        return mBookApiService.getRepliesList(id);
    }
    public Observable<BookHttpResponse<String>> postReplay(String id, String content){
        return mBookApiService.postReplay(id,content);
    }
    public Observable<BookHttpResponse<LoginBean>> postLogin(String email, String pass,String channelId){
        return mBookApiService.postLogin(email,pass,channelId);
    }
    public Observable<BookHttpResponse<FollowBean>> postFollow(String typeId){
        return mBookApiService.postFollow(typeId);
    }

    public Observable<BookHttpResponse<LoginBean>> postCreateAccount(CreateAccountBean bean) {
        return  mBookApiService.postCreateAccount(bean);
    }
}
