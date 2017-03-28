package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.SectionListBean;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.presenter.contract.SectionContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by codeest on 16/8/12.
 */

public class SectionPresenter extends RxPresenter<SectionContract.View> implements SectionContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public SectionPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void getSectionData() {
        Subscription rxSubscription = mRetrofitHelper.fetchSectionListInfo()
                .compose(RxUtil.<SectionListBean>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<SectionListBean>(mView) {
                    @Override
                    public void onNext(SectionListBean sectionListBean) {
                        mView.showContent(sectionListBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
