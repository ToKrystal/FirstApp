package com.codeest.geeknews.presenter;

import com.codeest.geeknews.app.Constants;
import com.codeest.geeknews.base.RxPresenter;
import com.codeest.geeknews.model.bean.BookDetailBean;
import com.codeest.geeknews.model.bean.BookDetailExtraBean;
import com.codeest.geeknews.model.bean.NodeListBean;
import com.codeest.geeknews.model.bean.RealmLikeBean;
import com.codeest.geeknews.model.bean.RepliesListBean;
import com.codeest.geeknews.model.db.RealmHelper;
import com.codeest.geeknews.model.http.RetrofitHelper;
import com.codeest.geeknews.presenter.contract.BookDetailContract;
import com.codeest.geeknews.util.RxUtil;
import com.codeest.geeknews.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Func1;

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

    /**
     * 获取评论
     * @param topic_id
     */
    @Override
    public void getContent(String topic_id) {
        Subscription rxSubscription = mRetrofitHelper.fetchRepliesList(topic_id)
                .compose(RxUtil.<List<RepliesListBean>>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<List<RepliesListBean>>(mView) {
                    @Override
                    public void onNext(List<RepliesListBean> repliesListBeen) {
                        mView.showContent(repliesListBeen);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    /**
     * 获取主题信息
     * @param topic_id
     */
    @Override
    public void getTopInfo(String topic_id) {
        Subscription rxSubscription = mRetrofitHelper.fetchTopicInfo(topic_id)
                .compose(RxUtil.<List<NodeListBean>>rxSchedulerHelper())
                .filter(new Func1<List<NodeListBean>, Boolean>() {
                    @Override
                    public Boolean call(List<NodeListBean> nodeListBeen) {
                        return nodeListBeen.size() > 0;
                    }
                })
                .map(new Func1<List<NodeListBean>, NodeListBean>() {
                    @Override
                    public NodeListBean call(List<NodeListBean> nodeListBeen) {
                        return nodeListBeen.get(0);
                    }
                })
                .subscribe(new CommonSubscriber<NodeListBean>(mView) {
                    @Override
                    public void onNext(NodeListBean nodeListBean) {
                        mView.showTopInfo(nodeListBean);
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
