package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.GankItemBean;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.GankHttpResponse;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;
import com.chao.bookviki.presenter.contract.GirlContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by codeest on 16/8/19.
 */

public class GirlPresenter extends RxPresenter<GirlContract.View> implements GirlContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    public static final int NUM_OF_PAGE = 20;

    private int currentPage = 1;

    @Inject
    public GirlPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void getGirlData() {
        currentPage = 1;
        Subscription rxSubscription = mRetrofitHelper.fetchGirlList(NUM_OF_PAGE,currentPage)
                .compose(RxUtil.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GankItemBean>>handleResult())
                .subscribe(new CommonSubscriber<List<GankItemBean>>(mView) {
                    @Override
                    public void onNext(List<GankItemBean> gankItemBeen) {
                        mView.showContent(gankItemBeen);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getMoreGirlData() {
        Subscription rxSubscription = mRetrofitHelper.fetchGirlList(NUM_OF_PAGE,++currentPage)
                .compose(RxUtil.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GankItemBean>>handleResult())
                .subscribe(new CommonSubscriber<List<GankItemBean>>(mView) {
                    @Override
                    public void onNext(List<GankItemBean> gankItemBeen) {
                        mView.showMoreContent(gankItemBeen);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
