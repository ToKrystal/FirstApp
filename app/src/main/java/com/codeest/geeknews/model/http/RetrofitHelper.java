package com.codeest.geeknews.model.http;

import com.codeest.geeknews.model.bean.BookCommentBean;
import com.codeest.geeknews.model.bean.BookDetailBean;
import com.codeest.geeknews.model.bean.BookDetailExtraBean;
import com.codeest.geeknews.model.bean.BookListBean;
import com.codeest.geeknews.model.bean.CommentBean;
import com.codeest.geeknews.model.bean.DailyBeforeListBean;
import com.codeest.geeknews.model.bean.DailyListBean;
import com.codeest.geeknews.model.bean.DetailExtraBean;
import com.codeest.geeknews.model.bean.GankItemBean;
import com.codeest.geeknews.model.bean.GankSearchItemBean;
import com.codeest.geeknews.model.bean.GoldListBean;
import com.codeest.geeknews.model.bean.HotListBean;
import com.codeest.geeknews.model.bean.NodeBean;
import com.codeest.geeknews.model.bean.NodeListBean;
import com.codeest.geeknews.model.bean.RepliesListBean;
import com.codeest.geeknews.model.bean.SectionChildListBean;
import com.codeest.geeknews.model.bean.SectionListBean;
import com.codeest.geeknews.model.bean.ThemeChildListBean;
import com.codeest.geeknews.model.bean.ThemeListBean;
import com.codeest.geeknews.model.bean.VersionBean;
import com.codeest.geeknews.model.bean.WelcomeBean;
import com.codeest.geeknews.model.bean.ZhihuDetailBean;
import com.codeest.geeknews.model.http.api.BookApis;
import com.codeest.geeknews.model.http.api.GankApis;
import com.codeest.geeknews.model.http.api.GoldApis;
import com.codeest.geeknews.model.http.api.MyApis;
import com.codeest.geeknews.model.http.api.VtexApis;
import com.codeest.geeknews.model.http.api.ZhihuApis;
import com.codeest.geeknews.model.http.response.BookHttpResponse;
import com.codeest.geeknews.model.http.response.GankHttpResponse;
import com.codeest.geeknews.model.http.response.GoldHttpResponse;
import com.codeest.geeknews.model.http.response.MyHttpResponse;

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
    public Observable<BookDetailBean> fetchBookDetailInfo(int id) {
        return mBookApiService.getBookDetailInfo(id);
    }

    public Observable<DetailExtraBean> fetchDetailExtraInfo(int id) {
        return mZhihuApiService.getDetailExtraInfo(id);
    }
    public Observable<BookDetailExtraBean> fetchBookDetailExtraInfo(int id) {
        return mBookApiService.getBookDetailExtraInfo(id);
    }

    public Observable<WelcomeBean> fetchWelcomeInfo(String res) {
        return mZhihuApiService.getWelcomeInfo(res);
    }

    public Observable<CommentBean> fetchLongCommentInfo(int id) {
        return mZhihuApiService.getLongCommentInfo(id);
    }

    public Observable<BookCommentBean> fetchBookLongCommentInfo(int id) {
        return mBookApiService.getBookLongCommentInfo(id);
    }

    public Observable<CommentBean> fetchShortCommentInfo(int id) {
        return mZhihuApiService.getShortCommentInfo(id);
    }
    public Observable<BookCommentBean> fetchBookShortCommentInfo(int id) {
        return mBookApiService.getBookShortCommentInfo(id);
    }

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
    public Observable<GoldHttpResponse<List<GoldListBean>>> fetchGoldList(String type, int start, int number) {
        return mGoldApiService.getGoldList(type,start,number);
       /* return mGoldApiService.getGoldList(Constants.LEANCLOUD_ID,
                Constants.LEANCLOUD_SIGN,
                "{\"category\":\"" + type + "\"}",
                "-createdAt",
                "user,user.installation",
                number,
                start * number);*/
    }
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
    public Observable<GoldHttpResponse<List<GoldListBean>>> fetchGoldHotList(String type, String dataTime, int limit) {
       return mGoldApiService.getGoldList("android",0,3);
        /*return mGoldApiService.getGoldList(Constants.LEANCLOUD_ID,
                Constants.LEANCLOUD_SIGN,
                "{\"category\":\"" + type + "\"}",
                "-createdAt",
                "user,user.installation",
                3,
                3 * 4);*/
    }

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

    public Observable<List<RepliesListBean>> fetchRepliesList(String id){
        return mVtexApiService.getRepliesList(id);
    }
}
