package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.NodeBean;
import com.chao.bookviki.model.bean.NodeListBean;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.presenter.contract.NodeContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by codeest on 16/12/23.
 */

public class NodePresenter extends RxPresenter<NodeContract.View> implements NodeContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public NodePresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void getContent(String node_name) {
        Subscription rxSubscription = mRetrofitHelper.fetchTopicList(node_name)
                .compose(RxUtil.<List<NodeListBean>>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<List<NodeListBean>>(mView) {
                    @Override
                    public void onNext(List<NodeListBean> nodeListBeen) {
                        mView.showContent(nodeListBeen);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getTopInfo(String node_name) {
        Subscription rxSubscription = mRetrofitHelper.fetchNodeInfo(node_name)
                .compose(RxUtil.<NodeBean>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<NodeBean>(mView) {
                    @Override
                    public void onNext(NodeBean nodeBean) {
                        mView.showTopInfo(nodeBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
