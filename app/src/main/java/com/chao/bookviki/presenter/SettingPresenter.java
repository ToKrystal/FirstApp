package com.chao.bookviki.presenter;

import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.model.bean.VersionBean;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.presenter.contract.SettingContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by codeest on 16/10/17.
 */

public class SettingPresenter extends RxPresenter<SettingContract.View> implements SettingContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public SettingPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void checkVersion(final String currentVersion) {
        Subscription rxSubscription = mRetrofitHelper.getCurrentVersion()
                .compose(RxUtil.<BookHttpResponse<VersionBean>>rxSchedulerHelper())
                .compose(RxUtil.<VersionBean>handleBookResult())
                .subscribe(new CommonSubscriber<VersionBean>(mView, "获取版本信息失败 T T") {
                    @Override
                    public void onNext(VersionBean versionBean) {
                        if (Integer.valueOf(currentVersion.replace(".", "")) < Integer.valueOf(versionBean.getCode().replace(".", ""))) {
                            mView.showUpdateDialog(versionBean);
                        } else {
                            mView.showError("亲！已经是最新版本了！");
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
