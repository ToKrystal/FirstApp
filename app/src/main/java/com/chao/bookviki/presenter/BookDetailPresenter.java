package com.chao.bookviki.presenter;

import com.chao.bookviki.app.Constants;
import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.BookListBean;
import com.chao.bookviki.model.bean.RealmLikeBean;
import com.chao.bookviki.model.bean.RepliesListBean;
import com.chao.bookviki.model.db.RealmHelper;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.presenter.contract.BookDetailContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Jessica on 2017/3/21.
 */

public class BookDetailPresenter extends RxPresenter<BookDetailContract.View> implements BookDetailContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;
   // private BookDetailBean mData;
    //private NodeListBean mData;
    private BookListBean mData;


    @Inject
    public BookDetailPresenter(RetrofitHelper mRetrofitHelper,RealmHelper mRealmHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mRealmHelper = mRealmHelper;
    }

    /*@Override
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
    }*/

    /*@Override
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
    }*/

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
     * 获取单个主题 供Push到Detail
     */
    @Override
    public void getSingleBookList(String objectId) {
        Subscription rxSubscription = mRetrofitHelper.fetchSingleInfo(objectId)
                .compose(RxUtil.<BookHttpResponse<List<BookListBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<BookListBean>>handleBookResult())//response-> bean
                .subscribe(new CommonSubscriber<List<BookListBean>>(mView) {
                    @Override
                    public void onNext(List<BookListBean> beans) {
                        mView.showTopInfo(beans.get(0));
                    }
                });
        addSubscrebe(rxSubscription);

    }

    @Override
    public void replayValidate() {
        if ( !mRealmHelper.queryIfLogin()){//没有登录
            mView.jump2LoginPage();
        }else {
            mView.showReplayView();
        }

    }

    /**
     * 获取主题信息
     *
     */
   /* @Override
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
    }*/

    @Override
    public void replay(String id,String content) {
        if(mRealmHelper.queryIfLogin()){
            Subscription rxSubscription = mRetrofitHelper.postReplay(id,content)
                    .compose(RxUtil.<BookHttpResponse<String>>rxSchedulerHelper())
                    .compose(RxUtil.<String>handleBookResult())
                    .subscribe(new CommonSubscriber<String>(mView) {
                        @Override
                        public void onNext(String str) {
                            //mData = bean;
                            //    mView.jump2CreateSucc(bean);
                            mView.showReplaySucc();
                        }
                    });
            addSubscrebe(rxSubscription);
        }else {
            mView.jump2LoginPage();
        }
    }

    @Override
    public void save2DetailPrestener(BookListBean bean) {
        mData = bean;
    }


    @Override
    public void insertLikeData() {
        if (mData != null) {
            RealmLikeBean bean = new RealmLikeBean();
            bean.setId(mData.getObjectId());
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
            mRealmHelper.deleteLikeBean(String.valueOf(mData.getObjectId()));
        } else {
            mView.showError("操作失败");
        }
    }

    @Override
    public void queryLikeData(int id) {
        mView.setLikeButtonState(mRealmHelper.queryLikeId(String.valueOf(id)));
    }
}
