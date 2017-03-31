package com.chao.bookviki.presenter;

import com.chao.bookviki.app.Constants;
import com.chao.bookviki.model.bean.RepliesListBean;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.widget.CommonSubscriber;
import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.BookDetailBean;
import com.chao.bookviki.model.bean.BookDetailExtraBean;
import com.chao.bookviki.model.bean.NodeListBean;
import com.chao.bookviki.model.bean.RealmLikeBean;
import com.chao.bookviki.model.db.RealmHelper;
import com.chao.bookviki.presenter.contract.BookDetailContract;
import com.chao.bookviki.util.RxUtil;

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
   // private BookDetailBean mData;
    private NodeListBean mData;

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
                     //   mData = bookDetailBean;
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
                        mView.showReplay(repliesListBeen);
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
                        mData = nodeListBean;
                        mView.showTopInfo(nodeListBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }



    @Override
    public void insertLikeData() {
        if (mData != null) {
            RealmLikeBean bean = new RealmLikeBean();
            bean.setId(mData.getId());
            bean.setImage("");
            bean.setTitle(mData.getTitle());
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
