package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.presenter.contract.SectionChildContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;
import com.chao.bookviki.model.bean.SectionChildListBean;
import com.chao.bookviki.model.db.RealmHelper;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by codeest on 16/8/28.
 */

public class SectionChildPresenter extends RxPresenter<SectionChildContract.View> implements SectionChildContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;

    @Inject
    public SectionChildPresenter(RetrofitHelper mRetrofitHelper, RealmHelper mRealmHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mRealmHelper = mRealmHelper;
    }

    @Override
    public void getThemeChildData(int id) {
        Subscription rxSubscription = mRetrofitHelper.fetchSectionChildListInfo(id)
                .compose(RxUtil.<SectionChildListBean>rxSchedulerHelper())
                .map(new Func1<SectionChildListBean, SectionChildListBean>() {
                    @Override
                    public SectionChildListBean call(SectionChildListBean sectionChildListBean) {
                        List<SectionChildListBean.StoriesBean> list = sectionChildListBean.getStories();
                        for(SectionChildListBean.StoriesBean item : list) {
                            item.setReadState(mRealmHelper.queryNewsId(item.getId()));
                        }
                        return sectionChildListBean;
                    }
                })
                .subscribe(new CommonSubscriber<SectionChildListBean>(mView) {
                    @Override
                    public void onNext(SectionChildListBean sectionChildListBean) {
                        mView.showContent(sectionChildListBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void insertReadToDB(int id) {
        mRealmHelper.insertNewsId(id);
    }
}
