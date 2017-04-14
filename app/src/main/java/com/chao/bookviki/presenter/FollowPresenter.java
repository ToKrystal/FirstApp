package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.FollowBean;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.presenter.contract.FollowContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;


import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Administrator on 2017/4/14.
 */

public class FollowPresenter extends RxPresenter<FollowContract.View> implements FollowContract.Presenter{
    private RetrofitHelper mRetrofitHelper;



    @Inject
    public FollowPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void postFollow(String objectId) {

        Subscription rxSubscription = mRetrofitHelper.postFollow(objectId)
                .compose(RxUtil.<BookHttpResponse<FollowBean>>rxSchedulerHelper())
                .compose(RxUtil.<FollowBean>handleBookResult())
                .subscribe(new CommonSubscriber<FollowBean>(mView) {
                    @Override
                    public void onNext(FollowBean bean) {
                       mView.showFollowSuc();
                    }
                });
        addSubscrebe(rxSubscription);

    }
}
