package com.codeest.geeknews.presenter;

import com.codeest.geeknews.app.Constants;
import com.codeest.geeknews.base.RxPresenter;
import com.codeest.geeknews.model.bean.BookDetailBean;
import com.codeest.geeknews.model.bean.BookDetailExtraBean;
import com.codeest.geeknews.model.bean.RealmLikeBean;
import com.codeest.geeknews.model.db.RealmHelper;
import com.codeest.geeknews.model.http.RetrofitHelper;
import com.codeest.geeknews.presenter.contract.BookDetailContract;
import com.codeest.geeknews.util.RxUtil;
import com.codeest.geeknews.widget.CommonSubscriber;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Jessica on 2017/3/21.
 */

public class BookDetailPresenter extends RxPresenter<BookDetailContract.View> implements BookDetailContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;
    private BookDetailBean mData;

    @Inject
    public BookDetailPresenter(RetrofitHelper mRetrofitHelper,RealmHelper mRealmHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mRealmHelper = mRealmHelper;
    }

    @Override
    public void getDetailData(int id) {
        Subscription rxSubscription =  mRetrofitHelper.fetchBookDetailInfo(id)
                .compose(RxUtil.<BookDetailBean>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<BookDetailBean>(mView) {
                    @Override
                    public void onNext(BookDetailBean bookDetailBean) {
                        mData = bookDetailBean;
                        mView.showContent(bookDetailBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getExtraData(int id) {
        Subscription rxSubscription =  mRetrofitHelper.fetchBookDetailExtraInfo(id)
                .compose(RxUtil.<BookDetailExtraBean>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<BookDetailExtraBean>(mView, "加载额外信息失败ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(BookDetailExtraBean bookDetailExtraBean) {
                        mView.showExtraInfo(bookDetailExtraBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void insertLikeData() {
        if (mData != null) {
            RealmLikeBean bean = new RealmLikeBean();
            bean.setId(String.valueOf(1 ));
            bean.setImage("");
            bean.setTitle("");
            bean.setType(Constants.TYPE_BOOK);
            bean.setTime(System.currentTimeMillis());
            mRealmHelper.insertLikeBean(bean);
        } else {
            mView.showError("操作失败");
        }
    }

    @Override
    public void deleteLikeData() {
        if (mData != null) {
            mRealmHelper.deleteLikeBean(String.valueOf(1));
        } else {
            mView.showError("操作失败");
        }
    }

    @Override
    public void queryLikeData(int id) {
        mView.setLikeButtonState(mRealmHelper.queryLikeId(String.valueOf(id)));
    }
}
