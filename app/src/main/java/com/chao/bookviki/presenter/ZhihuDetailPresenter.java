package com.chao.bookviki.presenter;

import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;
import com.chao.bookviki.model.bean.DetailExtraBean;
import com.chao.bookviki.model.bean.RealmLikeBean;
import com.chao.bookviki.model.bean.ZhihuDetailBean;
import com.chao.bookviki.model.db.RealmHelper;
import com.chao.bookviki.presenter.contract.ZhihuDetailContract;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by codeest on 16/8/13.
 */

public class ZhihuDetailPresenter extends RxPresenter<ZhihuDetailContract.View> implements ZhihuDetailContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;
    private ZhihuDetailBean mData;

    @Inject
    public ZhihuDetailPresenter(RetrofitHelper mRetrofitHelper,RealmHelper mRealmHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mRealmHelper = mRealmHelper;
    }

    @Override
    public void getDetailData(int id) {
        Subscription rxSubscription =  mRetrofitHelper.fetchDetailInfo(id)
                .compose(RxUtil.<ZhihuDetailBean>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<ZhihuDetailBean>(mView) {
                    @Override
                    public void onNext(ZhihuDetailBean zhihuDetailBean) {
                        mData = zhihuDetailBean;
                        mView.showContent(zhihuDetailBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getExtraData(int id) {
        Subscription rxSubscription =  mRetrofitHelper.fetchDetailExtraInfo(id)
                .compose(RxUtil.<DetailExtraBean>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<DetailExtraBean>(mView, "加载额外信息失败ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(DetailExtraBean detailExtraBean) {
                        mView.showExtraInfo(detailExtraBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void insertLikeData() {
        if (mData != null) {
            RealmLikeBean bean = new RealmLikeBean();
            bean.setId(String.valueOf(mData.getId()));
            bean.setImage(mData.getImage());
            bean.setTitle(mData.getTitle());
            bean.setType(Constants.TYPE_ZHIHU);
            bean.setTime(System.currentTimeMillis());
            mRealmHelper.insertLikeBean(bean);
        } else {
            mView.showError("操作失败");
        }
    }

    @Override
    public void deleteLikeData() {
        if (mData != null) {
            mRealmHelper.deleteLikeBean(String.valueOf(mData.getId()));
        } else {
            mView.showError("操作失败");
        }
    }

    @Override
    public void queryLikeData(int id) {
        mView.setLikeButtonState(mRealmHelper.queryLikeId(String.valueOf(id)));
    }
}
