package com.chao.bookviki.presenter;

import android.Manifest;

import com.chao.bookviki.app.App;
import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.component.RxBus;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.model.event.NightModeEvent;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.MyHttpResponse;
import com.chao.bookviki.presenter.contract.MainContract;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.widget.CommonSubscriber;
import com.chao.bookviki.model.bean.VersionBean;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by codeest on 16/8/9.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public MainPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        registerEvent();
        registerLoginEvent();
    }

    private void registerLoginEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(LoginBean.class)
                .compose(RxUtil.<LoginBean>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<LoginBean>(mView, "登录显示异常ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(LoginBean bean) {
                       // mView.useNightMode(aBoolean);
                        mView.showLogInOutInfo(bean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    void registerEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(NightModeEvent.class)
                .compose(RxUtil.<NightModeEvent>rxSchedulerHelper())
                .map(new Func1<NightModeEvent, Boolean>() {
                    @Override
                    public Boolean call(NightModeEvent nightModeEvent) {
                        return nightModeEvent.getNightMode();
                    }
                })
                .subscribe(new CommonSubscriber<Boolean>(mView, "切换模式失败ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        mView.useNightMode(aBoolean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void checkVersion(final String currentVersion) {
        Subscription rxSubscription = mRetrofitHelper.fetchVersionInfo()
                .compose(RxUtil.<MyHttpResponse<VersionBean>>rxSchedulerHelper())
                .compose(RxUtil.<VersionBean>handleMyResult())
                .filter(new Func1<VersionBean, Boolean>() {
                    @Override
                    public Boolean call(VersionBean versionBean) {
                        return Integer.valueOf(currentVersion.replace(".", "")) < Integer.valueOf(versionBean.getCode().replace(".", ""));
                    }
                })
                .map(new Func1<VersionBean, String>() {
                    @Override
                    public String call(VersionBean bean) {
                        StringBuilder content = new StringBuilder("版本号: v");
                        content.append(bean.getCode());
                        content.append("\r\n");
                        content.append("版本大小: ");
                        content.append(bean.getSize());
                        content.append("\r\n");
                        content.append("更新内容:\r\n");
                        content.append(bean.getDes().replace("\\r\\n","\r\n"));
                        return content.toString();
                    }
                })
                .subscribe(new CommonSubscriber<String>(mView) {
                    @Override
                    public void onNext(String s) {
                        mView.showUpdateDialog(s);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions) {
        Subscription rxSubscription = rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            mView.startDownloadService();
                        } else {
                            mView.showError("下载应用需要文件写入权限哦~");
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void doLogout() {
        ClearableCookieJar cookieJar = App.getAppComponent().clearableCookieJar();
        cookieJar.clear();
    }
}
