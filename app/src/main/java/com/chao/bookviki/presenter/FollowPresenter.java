package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.component.RxBus;
import com.chao.bookviki.model.bean.BookManagerBean;
import com.chao.bookviki.model.bean.FollowBean;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.presenter.contract.FollowContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;


import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * BookFollowAct.adapter 发事件typeId followPresenter 收到事件发送关注请求
 */

public class FollowPresenter extends RxPresenter<FollowContract.View> implements FollowContract.Presenter{
    private RetrofitHelper mRetrofitHelper;



    @Inject
    public FollowPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        registerFollowBeanEvent();
       // registerEvent();
    }

    private void registerFollowBeanEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(FollowBean.class)
                .subscribe(new CommonSubscriber<FollowBean>(mView, "关注异常ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(FollowBean bean) {
                        // mView.useNightMode(aBoolean);
                        //  mView.showLogInInfo(bean);
                        postFollow(bean.typeId);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    /**
     * 注册事件 发送关注请求
     */
   /* private void registerEvent() {
        addRxBusSubscribe(BookManagerBean.class, new Action1<BookManagerBean>() {
            @Override
            public void call(BookManagerBean goldManagerBean) {
                //TODO
                postFollow("");
            }
        });
    }*/



    @Override
    public void postFollow(String typeId) {

        Subscription rxSubscription = mRetrofitHelper.postFollow(typeId)
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
