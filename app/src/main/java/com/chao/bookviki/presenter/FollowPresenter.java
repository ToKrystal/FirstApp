package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.component.RxBus;
import com.chao.bookviki.model.bean.FollowBean;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.presenter.contract.FollowContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;


import javax.inject.Inject;

import rx.Subscription;

/**
 * BookFollowAct.adapter 发事件typeId followPresenter 收到事件发送关注请求
 */

public class FollowPresenter extends RxPresenter<FollowContract.View> implements FollowContract.Presenter{
    private RetrofitHelper mRetrofitHelper;



    @Inject
    public FollowPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        registerFollowBeanEvent();
    }

    private void registerFollowBeanEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(FollowBean.class)
                .subscribe(new CommonSubscriber<FollowBean>(mView, "关注异常ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(FollowBean bean) {
                        // mView.useNightMode(aBoolean);
                        //  mView.showLogInInfo(bean);
                        postFollow(bean.typeId,bean.follow);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void postFollow(String typeId,boolean follow) {

        Subscription rxSubscription = mRetrofitHelper.postFollow(typeId,follow)
                .compose(RxUtil.<BookHttpResponse<FollowBean>>rxSchedulerHelper())
                .compose(RxUtil.<FollowBean>handleBookResult())
                .subscribe(new CommonSubscriber<FollowBean>(mView) {
                    @Override
                    public void onNext(FollowBean bean) {
                       if (bean.follow)
                        mView.showFollowSuc();
                        else mView.showUnFollowSuc();
                    }
                });
        addSubscrebe(rxSubscription);

    }
}
