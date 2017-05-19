package com.chao.bookviki.model.http;

import com.chao.bookviki.model.bean.BookListBean;
import com.chao.bookviki.model.bean.CreateAccountBean;
import com.chao.bookviki.model.bean.FollowBean;
import com.chao.bookviki.model.bean.ImageBean;
import com.chao.bookviki.model.bean.JingXuanNewsBean;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.model.bean.NodeBean;
import com.chao.bookviki.model.bean.NodeListBean;
import com.chao.bookviki.model.bean.PersonalInfoBean;
import com.chao.bookviki.model.bean.RepliesListBean;
import com.chao.bookviki.model.bean.VersionBean;
import com.chao.bookviki.model.bean.YingWenYuLuBean;
import com.chao.bookviki.model.http.api.BookApis;
import com.chao.bookviki.model.http.api.JingXuanNewsApis;
import com.chao.bookviki.model.http.api.MyApis;
import com.chao.bookviki.model.http.api.VtexApis;
import com.chao.bookviki.model.http.api.YingWenYuLuApis;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.model.http.response.JingXuanNewsResponse;
import com.chao.bookviki.model.http.response.YingWenYuLuResponse;

import java.util.List;

import rx.Observable;

/**
 *
 */
public class RetrofitHelper {

    private BookApis mBookApiService;
    private MyApis mMyApiService;
    private VtexApis mVtexApiService;
    private JingXuanNewsApis jingXuanNewsApis;
    private YingWenYuLuApis yingWenYuLuApis;

    public RetrofitHelper(
                          MyApis myApiService,  VtexApis vtexApiService,BookApis bookApis,JingXuanNewsApis jingXuanNewsApis,YingWenYuLuApis yingWenYuLuApis) {
        this.mMyApiService = myApiService;
        this.mVtexApiService = vtexApiService;
        this.mBookApiService = bookApis;
        this.jingXuanNewsApis = jingXuanNewsApis;
        this.yingWenYuLuApis = yingWenYuLuApis;
    }

    public Observable<BookHttpResponse<List<BookListBean>>> fetchBookList(String type, int start, int number) {
        return mBookApiService.getBookList(type,start,number);
    }

    public Observable<BookHttpResponse<List<BookListBean>>> fetchBookHotList(String type, String dataTime, int limit) {
        return mBookApiService.getBookList("android",0,3);
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
        return mBookApiService.getRepliesList(id);
    }

    public Observable<BookHttpResponse<List<BookListBean>>> fetchSingleInfo(String objectId){
        return  mBookApiService.getSingleBookList(objectId);
    }
    public Observable<BookHttpResponse<String>> postReplay(String id, String content){
        return mBookApiService.postReplay(id,content);
    }
    public Observable<BookHttpResponse<LoginBean>> postLogin(String email, String pass,String channelId){
        return mBookApiService.postLogin(email,pass,channelId);
    }
    public Observable<BookHttpResponse<FollowBean>> postFollow(String typeId,boolean follow){
        return mBookApiService.postFollow(typeId,follow);
    }

    public Observable<BookHttpResponse<LoginBean>> postCreateAccount(CreateAccountBean bean) {
        return  mBookApiService.postCreateAccount(bean);
    }
    public Observable<BookHttpResponse<String>> postUpdatePersonalInfo(PersonalInfoBean bean) {
        return  mBookApiService.postUpdatePersonInfo(bean);
    }

    public Observable<BookHttpResponse<String>> postChannelIdNotLogin(String channelId) {
        return  mBookApiService.postChannelIdNotLogin(channelId);
    }

    public Observable<BookHttpResponse<ImageBean>> getRandomImages(int num) {
        return  mBookApiService.getRandomImage(num);
    }

    public Observable<BookHttpResponse<VersionBean>> getCurrentVersion(){
        return mBookApiService.getCurrentVersion();
    }

    public Observable<JingXuanNewsResponse<List<JingXuanNewsBean>>> getJingXuanNewsList(String type,int page,int limit){
        return jingXuanNewsApis.getJingXuanNews(type,page,limit);
    }

    public Observable<YingWenYuLuResponse<List<YingWenYuLuBean>>> getYingWenYuLus(int count,int showapi_appid,String showapi_sign ){
        return yingWenYuLuApis.getYingWenYuLus(count,showapi_appid,showapi_sign);
    }
}
