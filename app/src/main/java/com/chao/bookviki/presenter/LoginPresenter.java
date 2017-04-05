package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.presenter.contract.LoginContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Jessica on 2017/4/4.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter{
    private RetrofitHelper mRetrofitHelper;
    @Inject
    public LoginPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }


    @Override
    public void postLogin(String email, String pass) {

        Subscription rxSubscription = mRetrofitHelper.postLogin(email,pass)
                .compose(RxUtil.<BookHttpResponse<LoginBean>>rxSchedulerHelper())
                .compose(RxUtil.<LoginBean>handleBookResult())
                .subscribe(new CommonSubscriber<LoginBean>(mView) {
                    @Override
                    public void onNext(LoginBean loginBeen) {
                        mView.jump2LoginSucc(loginBeen);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError("");
                    }
                });
        addSubscrebe(rxSubscription);

    }
}
