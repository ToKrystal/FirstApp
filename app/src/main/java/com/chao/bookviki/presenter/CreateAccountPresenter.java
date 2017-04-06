package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.CreateAccountBean;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.model.db.RealmHelper;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.presenter.contract.CreateAccountContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Jessica on 2017/4/5.
 */

public class CreateAccountPresenter extends RxPresenter<CreateAccountContract.View> implements CreateAccountContract.Presenter {
    private RetrofitHelper mRetrofitHelper;
    private LoginBean mData;
    private RealmHelper mRealmHelper;
    @Inject
    public CreateAccountPresenter(RetrofitHelper mRetrofitHelper,RealmHelper realmHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mRealmHelper = realmHelper;
    }



    @Override
    public void postCreateAccount(CreateAccountBean bean) {

        Subscription rxSubscription = mRetrofitHelper.postCreateAccount(bean)
                .compose(RxUtil.<BookHttpResponse<LoginBean>>rxSchedulerHelper())
                .compose(RxUtil.<LoginBean>handleBookResult())
                .subscribe(new CommonSubscriber<LoginBean>(mView) {
                    @Override
                    public void onNext(LoginBean bean) {
                        mData = bean;
                        mView.jump2CreateSucc(bean);

                    }
                    @Override
                    public void onError(Throwable e) {
                        mView.showError("");
                    }
                });
        addSubscrebe(rxSubscription);

    }

    @Override
    public void insertLoginBean() {
        if (mData != null){
            mRealmHelper.insertLoginBean(mData);
        }else {
            mView.showError("操作失败");
        }
    }
}
