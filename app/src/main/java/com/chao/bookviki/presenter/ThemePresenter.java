package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.presenter.contract.ThemeContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;
import com.chao.bookviki.model.bean.ThemeListBean;

import javax.inject.Inject;

/**
 * Created by codeest on 16/8/12.
 */

public class ThemePresenter extends RxPresenter<ThemeContract.View> implements ThemeContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public ThemePresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void getThemeData() {
        mRetrofitHelper.fetchDailyThemeListInfo()
                .compose(RxUtil.<ThemeListBean>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<ThemeListBean>(mView) {
                    @Override
                    public void onNext(ThemeListBean themeListBean) {
                        mView.showContent(themeListBean);
                    }
                });
    }
}
