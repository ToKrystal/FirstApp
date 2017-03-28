package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.CommentBean;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.presenter.contract.CommentContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by codeest on 16/8/18.
 */

public class CommentPresenter extends RxPresenter<CommentContract.View> implements CommentContract.Presenter{

    public static final int SHORT_COMMENT = 0;

    public static final int LONG_COMMENT = 1;

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public CommentPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }


    @Override
    public void getCommentData(int id, int commentKind) {
        if(commentKind == SHORT_COMMENT) {
            Subscription rxSubscription = mRetrofitHelper.fetchShortCommentInfo(id)
                    .compose(RxUtil.<CommentBean>rxSchedulerHelper())
                    .subscribe(new CommonSubscriber<CommentBean>(mView) {
                        @Override
                        public void onNext(CommentBean commentBean) {
                            mView.showContent(commentBean);
                        }
                    });
            addSubscrebe(rxSubscription);
        } else {
            Subscription rxSubscription = mRetrofitHelper.fetchLongCommentInfo(id)
                    .compose(RxUtil.<CommentBean>rxSchedulerHelper())
                    .subscribe(new CommonSubscriber<CommentBean>(mView) {
                        @Override
                        public void onNext(CommentBean commentBean) {
                            mView.showContent(commentBean);
                        }
                    });
            addSubscrebe(rxSubscription);
        }
    }
}
