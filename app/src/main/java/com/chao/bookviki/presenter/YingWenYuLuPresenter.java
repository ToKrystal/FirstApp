package com.chao.bookviki.presenter;

import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.component.RxBus;
import com.chao.bookviki.model.bean.ImageBean;
import com.chao.bookviki.model.bean.YingWenYuLuBean;
import com.chao.bookviki.model.event.SearchEvent;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.model.http.response.YingWenYuLuResponse;
import com.chao.bookviki.presenter.contract.YingWenYuLuContract;
import com.chao.bookviki.ui.gank.fragment.GankMainFragment;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Func1;

/**
 *
 */

public class YingWenYuLuPresenter extends RxPresenter<YingWenYuLuContract.View> implements YingWenYuLuContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    private String queryStr = null;
    private String currentTech = GankMainFragment.tabTitle[0];
    private int currentType = Constants.YU_LU_CONSTATNT;
    private List<YingWenYuLuBean> mList;


    @Inject
    public YingWenYuLuPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        mList = new ArrayList<>();
        //查询事件
        registerEvent();
    }

    private void registerEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(SearchEvent.class)
                .compose(RxUtil.<SearchEvent>rxSchedulerHelper())
                .filter(new Func1<SearchEvent, Boolean>() {
                    @Override
                    public Boolean call(SearchEvent searchEvent) {
                        return searchEvent.getType() == currentType;
                    }
                })
                .map(new Func1<SearchEvent, String>() {
                    @Override
                    public String call(SearchEvent searchEvent) {
                        return searchEvent.getQuery();
                    }
                })
                .subscribe(new CommonSubscriber<String>(mView, "搜索失败") {
                    @Override
                    public void onNext(String s) {
                        queryStr = s;
                     //   getSearchTechData();
                      //  getSearchNewsDate();
                        getSearchBeans(s);
                    }


                });
        addSubscrebe(rxSubscription);
    }

    private void getSearchBeans(String s) {
        List<YingWenYuLuBean> list = new ArrayList<>(mList.size());
        for (YingWenYuLuBean bean : mList){
            if (bean.getEnglish().contains(s)){
                list.add(bean);
            }
        }
        mView.showYingWenYuLus(list);
    }


    @Override
    public void getYingWenYuLu(int count, int appId, String sign, final boolean showMore) {
        Subscription rxSubscription = mRetrofitHelper.getYingWenYuLus(count,appId,sign)
                .compose(RxUtil.<YingWenYuLuResponse<List<YingWenYuLuBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<YingWenYuLuBean>>handleYingWenYuLuResult())
                .subscribe(new CommonSubscriber<List<YingWenYuLuBean>>(mView) {
                    @Override
                    public void onNext(List<YingWenYuLuBean> beans) {
                        if (showMore){
                            mView.showMoreYinWenYuLus(beans);
                            mList.addAll(beans);
                        }else {
                            mView.showYingWenYuLus(beans);
                            mList.clear();
                            mList.addAll(beans);
                        }

                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getRandomImage() {

        Subscription rxSubscription = mRetrofitHelper.getRandomImages(1)
                .compose(RxUtil.<BookHttpResponse<ImageBean>>rxSchedulerHelper())
                .compose(RxUtil.<ImageBean>handleBookResult())
                .subscribe(new CommonSubscriber<ImageBean>(mView, "加载封面失败") {
                    @Override
                    public void onNext(ImageBean gankItemBean) {
                        //  mView.showGirlImage(gankItemBean.get(0).getUrl(), gankItemBean.get(0).getWho());
                        //   mView.showGirlImage("http://chenyuchao.com.cn/1.jpg","chaoge");
                        mView.showImage(gankItemBean.url,gankItemBean.name);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
