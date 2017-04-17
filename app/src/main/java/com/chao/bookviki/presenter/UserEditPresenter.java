package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.PersonalInfoBean;
import com.chao.bookviki.model.db.RealmHelper;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.presenter.contract.UserEditContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Jessica on 2017/3/25.
 */

public class UserEditPresenter extends RxPresenter<UserEditContract.View> implements UserEditContract.Presenter {
    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;

    @Inject
    public UserEditPresenter(RealmHelper mRealHelper,RetrofitHelper mRetrofitHelper) {
        this.mRealmHelper = mRealmHelper;
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void postUpdatePersonalInfo(PersonalInfoBean bean) {
        Subscription rxSubscription = mRetrofitHelper.postUpdatePersonalInfo(bean)
                .compose(RxUtil.<BookHttpResponse<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleBookResult())
                .subscribe(new CommonSubscriber<String>(mView) {
                    @Override
                    public void onNext(String str) {


                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError("");
                    }
                });
        addSubscrebe(rxSubscription);



    }
}
