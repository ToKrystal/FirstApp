package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.presenter.contract.WelcomeContract;
import com.chao.bookviki.util.RxUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;


public class WelcomePresenter extends RxPresenter<WelcomeContract.View> implements WelcomeContract.Presenter {

    private static final String RES = "1080*1776";

    private static final int COUNT_DOWN_TIME = 2200;

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public WelcomePresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void getWelcomeData() {
        startCountDown();
    }

    private void startCountDown() {
        Subscription rxSubscription = Observable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS)//类似于handler 延迟发送一次
                .compose(RxUtil.<Long>rxSchedulerHelper())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mView.jumpToMain();
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
