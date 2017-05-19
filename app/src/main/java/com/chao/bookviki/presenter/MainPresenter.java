package com.chao.bookviki.presenter;

import android.Manifest;
import android.os.Handler;

import com.chao.bookviki.app.App;
import com.chao.bookviki.base.RxPresenter;
import com.chao.bookviki.component.RxBus;
import com.chao.bookviki.model.bean.LogOutBean;
import com.chao.bookviki.model.bean.LoginBean;
import com.chao.bookviki.model.bean.MyPushBean;
import com.chao.bookviki.model.bean.PushBindSucBean;
import com.chao.bookviki.model.bean.VersionBean;
import com.chao.bookviki.model.db.RealmHelper;
import com.chao.bookviki.model.event.NightModeEvent;
import com.chao.bookviki.model.http.RetrofitHelper;
import com.chao.bookviki.model.http.response.BookHttpResponse;
import com.chao.bookviki.presenter.contract.MainContract;
import com.chao.bookviki.util.LogUtil;
import com.chao.bookviki.util.RxUtil;
import com.chao.bookviki.util.SharedPreferenceUtil;
import com.chao.bookviki.widget.CommonSubscriber;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;



public class  MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;
    private Handler handler;

    @Inject
    public MainPresenter(RetrofitHelper mRetrofitHelper,RealmHelper realmHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mRealmHelper =  realmHelper;
        registerEvent();
        registerLoginEvent();
        registerLogOutEvent();
        registerPushEvent();
        registerPushBindEvent();
        registerTimeTask();
    }

    private void registerTimeTask() {
        handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
               String channelId =  SharedPreferenceUtil.getBaiDuYunChannelId();
                if (channelId != null && SharedPreferenceUtil.getBaiYunBindState() && !SharedPreferenceUtil.getPushStateNotLogin()){
                    PushBindSucBean bean =  new PushBindSucBean(channelId);
                    RxBus.getDefault().post(bean);
                    SharedPreferenceUtil.setPushStateNotLogin();
                }
                //要做的事情
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(runnable, 5000);//每两秒执行一次runnable.
      //  handler.removeCallbacks(runnable);
    }

    private void registerPushEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(MyPushBean.class)
                .subscribe(new CommonSubscriber<MyPushBean>(mView, "跳转异常ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(MyPushBean bean) {
                        // mView.useNightMode(aBoolean);
                      //  mView.showLogInInfo(bean);
                        mView.jump2PushSucc(bean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    private void registerPushBindEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(PushBindSucBean.class)
                .subscribe(new CommonSubscriber<PushBindSucBean>(mView, "默认异常ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(PushBindSucBean bean) {
                       postChannelIdNotLogin(bean.channelId);
                    }
                });
        addSubscrebe(rxSubscription);
    }


    //注册登录事件
    void registerLoginEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(LoginBean.class)
                .subscribe(new CommonSubscriber<LoginBean>(mView, "登录显示异常ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(LoginBean bean) {
                        // mView.useNightMode(aBoolean);
                        mView.showLogInInfo(bean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    //注册登出事件
    void registerLogOutEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(LogOutBean.class)
                .subscribe(new CommonSubscriber<LogOutBean>(mView, "注销异常ヽ(≧Д≦)ノ") {
                    @Override
                    public void onNext(LogOutBean bean) {
                        // mView.useNightMode(aBoolean);
                        mView.showLogOutInfo();
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
        Subscription rxSubscription = mRetrofitHelper.getCurrentVersion()
                .compose(RxUtil.<BookHttpResponse<VersionBean>>rxSchedulerHelper())
                .compose(RxUtil.<VersionBean>handleBookResult())
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
                        return content.toString()+"_"+bean.getDownloadUrl();
                    }
                })
                .subscribe(new CommonSubscriber<String>(mView) {
                    @Override
                    public void onNext(String s) {
                        String[] strArr = s.split("_");
                        mView.showUpdateDialog(strArr[0],strArr[1]);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions, final String downloadUrl) {
        Subscription rxSubscription = rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            mView.startDownloadService(downloadUrl);
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

    @Override
    public void deleteLoginBean(LoginBean bean) {
        mRealmHelper.deleteLoginBean(bean.name);

    }

    @Override
    public void queryLoginState() {
       LoginBean bean = mRealmHelper.returnLoginBean();
        if (bean == null){
            mView.showDefaultUserInfo();
        }else {
            mView.showLogInInfo(bean);
        }
    }

    @Override
    public void postChannelIdNotLogin(String channelId) {
        Subscription rxSubscription = mRetrofitHelper.postChannelIdNotLogin(channelId)
                .compose(RxUtil.<BookHttpResponse<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleBookResult())
                .subscribe(new CommonSubscriber<String>(mView) {
                    @Override
                    public void onNext(String String) {
                        LogUtil.i("无登录状态时post channelId 成功");
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i("无登录状态时post channelId 失败");
                        mView.showError("");
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public boolean queryIfLogin() {
        return mRealmHelper.queryIfLogin();
    }
}
