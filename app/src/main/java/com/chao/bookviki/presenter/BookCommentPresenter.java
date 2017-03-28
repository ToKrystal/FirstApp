package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.presenter.contract.BookCommentContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;
import com.chao.bookviki.model.bean.BookCommentBean;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by codeest on 16/8/18.
 */

public class BookCommentPresenter extends RxPresenter<BookCommentContract.View> implements BookCommentContract.Presenter {

    public static final int SHORT_COMMENT = 0;

    public static final int LONG_COMMENT = 1;

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public BookCommentPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }


    @Override
    public void getCommentData(int id, int commentKind) {
        if(commentKind == SHORT_COMMENT) {
            Subscription rxSubscription = mRetrofitHelper.fetchBookShortCommentInfo(id)
                    .compose(RxUtil.<BookCommentBean>rxSchedulerHelper())
                    .subscribe(new CommonSubscriber<BookCommentBean>(mView) {
                        @Override
                        public void onNext(BookCommentBean bookCommentBean) {
                            mView.showContent(bookCommentBean);
                        }
                    });
            addSubscrebe(rxSubscription);
        } else {
            Subscription rxSubscription = mRetrofitHelper.fetchBookLongCommentInfo(id)
                    .compose(RxUtil.<BookCommentBean>rxSchedulerHelper())
                    .subscribe(new CommonSubscriber<BookCommentBean>(mView) {
                        @Override
                        public void onNext(BookCommentBean bookCommentBean) {
                            mView.showContent(bookCommentBean);
                        }
                    });
            addSubscrebe(rxSubscription);
        }
    }
}
