package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.component.RxBus;
import com.chao.bookviki.model.bean.LogOutBean;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.model.db.RealmHelper;
import com.chao.bookviki.presenter.contract.UserInfoContract;
import com.chao.bookviki.widget.CommonSubscriber;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Jessica on 2017/3/25.
 */

public class UserInfoPresenter extends RxPresenter<UserInfoContract.View> implements UserInfoContract.Presenter {

    private RealmHelper mRealmHelper;


    @Inject
    public UserInfoPresenter(RealmHelper mRealHelper) {
        this.mRealmHelper = mRealHelper;
        registerLoginEvent();
        registerLogOutEvent();
    }

    //注册登录事件
    void registerLoginEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(LoginBean.class)
                .subscribe(new CommonSubscriber<LoginBean>(mView, "登录显示异常ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(LoginBean bean) {
                        // mView.useNightMode(aBoolean);
                        mView.showContent(bean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    //注册登出事件
    void registerLogOutEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(LogOutBean.class)
                .subscribe(new CommonSubscriber<LogOutBean>(mView, "注销异常ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(LogOutBean bean) {
                        // mView.useNightMode(aBoolean);
                        mView.showDefaultUserInfo();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void seteditData() {
        mView.jumpToEdit();
    }

    @Override
    public void queryLoginBean() {
        LoginBean bean = mRealmHelper.returnLoginBean();
        if (bean == null) mView.showDefaultUserInfo();
        else
        mView.showContent(bean);
    }
}
