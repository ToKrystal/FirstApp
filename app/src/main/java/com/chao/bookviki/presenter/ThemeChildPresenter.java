package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.ThemeChildListBean;
import com.chao.bookviki.model.db.RealmHelper;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.presenter.contract.ThemeChildContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by codeest on 16/8/24.
 */

public class ThemeChildPresenter extends RxPresenter<ThemeChildContract.View> implements ThemeChildContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;

    @Inject
    public ThemeChildPresenter(RetrofitHelper mRetrofitHelper, RealmHelper mRealmHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mRealmHelper = mRealmHelper;
    }

    @Override
    public void getThemeChildData(int id) {
        Subscription rxSubscription = mRetrofitHelper.fetchThemeChildListInfo(id)
                .compose(RxUtil.<ThemeChildListBean>rxSchedulerHelper())
                .map(new Func1<ThemeChildListBean, ThemeChildListBean>() {
                    @Override
                    public ThemeChildListBean call(ThemeChildListBean themeChildListBean) {
                        List<ThemeChildListBean.StoriesBean> list = themeChildListBean.getStories();
                        for(ThemeChildListBean.StoriesBean item : list) {
                            item.setReadState(mRealmHelper.queryNewsId(item.getId()));
                        }
                        return themeChildListBean;
                    }
                })
                .subscribe(new CommonSubscriber<ThemeChildListBean>(mView) {
                    @Override
                    public void onNext(ThemeChildListBean themeChildListBean) {
                        mView.showContent(themeChildListBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void insertReadToDB(int id) {
        mRealmHelper.insertNewsId(id);
    }
}
